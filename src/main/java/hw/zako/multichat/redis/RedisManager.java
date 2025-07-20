package hw.zako.multichat.redis;

import hw.zako.multichat.Config;
import hw.zako.multichat.MultiChat;
import hw.zako.multichat.events.MultiChatMessageEvent;
import hw.zako.multichat.redis.packet.list.ChatMessagePacket;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RedisManager {
    private static final String CHANNEL = "MULTICHAT_CHAT";
    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;

    private final HashMap<Player, Boolean> chatToggle = new HashMap<>();

    private final MultiChat plugin;

    public RedisManager(MultiChat plugin) {
        this.plugin = plugin;

        this.client = getRedisClient();
        if (client == null) {
            plugin.getLogger().info("Не удалось подключиться к Redis.");
            return;
        }

        connection = client.connect();
        final StatefulRedisPubSubConnection<String, String> pubSubConnection = client.connectPubSub();

        pubSubConnection.addListener(new RedisMessageListener() {
            @Override
            public void message(String channel, String message) {
                final ChatMessagePacket packet = new ChatMessagePacket(message);

                final MultiChatMessageEvent event = new MultiChatMessageEvent(packet.getUsername(), packet.getMessage());
                event.callEvent();
                if (event.isCancelled()) return;

                final String msg = Config.MULTICHAT.format
                        .replace("{sender}", event.getSender())
                        .replace("{message}", event.getMessage());

                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.hasPermission(Config.MULTICHAT.permissionUse) && !isChatToggle(player)) {
                        player.sendMessage(msg);
                    }
                });
            }
        });

        pubSubConnection.async().subscribe(CHANNEL);
    }

    private RedisClient getRedisClient() {
        try {
            plugin.getLogger().info("Подключась к Redis..");
            return RedisClient.create("redis://"+ Config.redisConfig.host()+":"+Config.redisConfig.port());
        } catch (Throwable throwable) {
            plugin.getLogger().info("Произошла ошибка при подключении к Redis: " + throwable);
            return null;
        }
    }

    public void unload() {
        client.shutdown();
    }

    public void sendMessage(String sender, String message) {
        final RedisAsyncCommands<String, String> commands = connection.async();
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
