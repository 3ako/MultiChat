// dev by alexec0de
package hw.zako.multichat.command;

import hw.zako.multichat.Config;
import hw.zako.multichat.redis.RedisManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@AllArgsConstructor
public class MultiChatToggleCommand implements CommandExecutor {


    private final RedisManager redisManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)){
            sender.sendMessage(ChatColor.RED + "Использовать мультичат может только игрок!");
            return true;
        }

        if (redisManager.isChatToggle(p)){
            sender.sendMessage(Config.chatOff);
        } else {
            sender.sendMessage(Config.chatOn);
        }
        redisManager.setChatToggle(p);

        return true;
    }
}
