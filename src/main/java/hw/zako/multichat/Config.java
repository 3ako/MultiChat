package hw.zako.multichat;

import hw.zako.multichat.exception.ConfigSectionInvalid;
import hw.zako.multichat.util.Colorizer;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;

@UtilityClass
public class Config {

    private FileConfiguration file;

    public void load(FileConfiguration config) {
        file = config;

       parseRedis();
       parseMultiChat();
    }

    private void parseRedis() {
        final ConfigurationSection redisSection = file.getConfigurationSection("redis");
        if (redisSection == null) throw new ConfigSectionInvalid("Redis");

        final String host = redisSection.getString("host");
        final int port = redisSection.getInt("port");
        final int timeout = redisSection.getInt("timeout");

        redisConfig = new RedisConfig(host, port, timeout);
    }

    private void parseMultiChat() {
        final ConfigurationSection multiChatSection = file.getConfigurationSection("multi-chat");
        if (multiChatSection == null) throw new ConfigSectionInvalid("Multi-chat");

        MULTICHAT.permissionUse = new Permission(multiChatSection.getString("permission-use"));
        MULTICHAT.format = Colorizer.use(multiChatSection.getString("format"));
        MULTICHAT.on = Colorizer.use(multiChatSection.getString("on"));
        MULTICHAT.off = Colorizer.use(multiChatSection.getString("off"));
    }

    public RedisConfig redisConfig;
    public record RedisConfig(String host, int port, int timeout) {}

    @UtilityClass
    public class MULTICHAT {
        public Permission permissionUse;
        public String format, on, off;
    }
}
