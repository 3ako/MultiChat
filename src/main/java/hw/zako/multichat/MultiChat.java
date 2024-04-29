package hw.zako.multichat;

import hw.zako.multichat.command.SendMessageCommand;
import hw.zako.multichat.redis.RedisManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiChat extends JavaPlugin {

    private RedisManager redisManager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.load(getConfig());

        redisManager = new RedisManager();

        getCommand("multichat").setExecutor(new SendMessageCommand(redisManager));
    }

    @Override
    public void onDisable() {
        if (redisManager != null) {
            redisManager.unload();
        }
    }
}
