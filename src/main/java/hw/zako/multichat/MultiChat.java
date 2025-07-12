package hw.zako.multichat;

import hw.zako.multichat.command.MultiChatToggleCommand;
import hw.zako.multichat.command.SendMessageCommand;
import hw.zako.multichat.redis.RedisManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class MultiChat extends JavaPlugin {

    private RedisManager redisManager;

    @Override
    public void onEnable() {
        super.saveDefaultConfig();
        Config.load(super.getConfig());

        redisManager = new RedisManager(this);

        super.getCommand("multichat").setExecutor(new SendMessageCommand(redisManager));
        super.getCommand("multichattoggle").setExecutor(new MultiChatToggleCommand(redisManager));
    }

    @Override
    public void onDisable() {
        if (redisManager != null) redisManager.unload();
    }
}
