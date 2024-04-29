package hw.zako.multichat;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;

@UtilityClass
public class Config {
    public static void load(FileConfiguration file) {
        chatUserPermission = new Permission(file.getString("chat-permission"));

       final ConfigurationSection redisSection = file.getConfigurationSection("redis");
       if (redisSection == null) {
           throw new IllegalStateException("Redis config is invalid");
       }
       parseRedis(redisSection);

       chatFormat = file.getString("chat-format");
    }

    public static String chatFormat;
    private static void parseRedis(ConfigurationSection section) {
        final String host = section.getString("host");
        final int port = section.getInt("port");
        final int timeout = section.getInt("timeout");
        redisConfig = new RedisConfig(host, port, timeout);
    }

    public static RedisConfig redisConfig;
    public static Permission chatUserPermission;

    public record RedisConfig(String host, int port, int timeout) {
    }
}
