package hw.zako.multichat.redis;

import hw.zako.multichat.Config;
import hw.zako.multichat.events.MultiChatMessageEvent;
import hw.zako.multichat.redis.packet.list.ChatMessagePacket;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RedisManager {
    private static final String CHANNEL = "MULTICHAT_CHAT";
    private final RedisClient client;
    private final StatefulRedisConnection<String, String> connection;
    private final StatefulRedisPubSubConnection<String, String> pubSubConnection;

    private final HashMap<Player, Boolean> chatToggle = new HashMap<>();


    public RedisManager() {
        this.client = RedisClient.create("redis://"+ Config.redisConfig.host()+":"+Config.redisConfig.port());
        connection = client.connect();
        pubSubConnection = client.connectPubSub();

        pubSubConnection.addListener(new RedisMessageListener() {
            @Override
            public void message(String channel, String message) {
                final ChatMessagePacket packet = new ChatMessagePacket(message);

                final MultiChatMessageEvent event = new MultiChatMessageEvent(packet.getUsername(), packet.getMessage());
                event.callEvent();
                if (event.isCancelled()) return;

                final String msg = Config.chatFormat
                        .replace("%sender%", event.getSender())
                        .replace("%message%", event.getMessage());
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.hasPermission(Config.chatUserPermission) && !isChatToggle(player)) {
                        player.sendMessage(msg);
                    }
                });
                Bukkit.getLogger().info(msg);
            }
        });

        pubSubConnection.async().subscribe(CHANNEL);
    }

    public void unload() {
        client.shutdown();
    }

    public void sendMessage(String sender, String message) {
        RedisAsyncCommands<String, String> commands = connection.async();
        final var packet = new ChatMessagePacket(sender, message);
        packet.write();
        commands.publish(CHANNEL, packet.getSource());
    }

    public boolean isChatToggle(Player player){
        return chatToggle.containsKey(player);
    }

    public void setChatToggle(Player player){
        if (chatToggle.containsKey(player)){
            chatToggle.remove(player);
        } else {
            chatToggle.put(player, true);
        }
    }
}
