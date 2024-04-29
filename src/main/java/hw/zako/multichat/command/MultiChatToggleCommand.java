// dev by alexec0de
package hw.zako.multichat.command;

import hw.zako.multichat.redis.RedisManager;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
@AllArgsConstructor
public class MultiChatToggleCommand implements CommandExecutor {

    private final RedisManager redisManager;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Эту команду можно выполнить только в игре");
            return true;
        }
        Player player = (Player) sender;
        if (redisManager.getChatToggle().containsKey(player)){
            redisManager.getChatToggle().remove(player);
        } else {
            redisManager.getChatToggle().put(player, true);
        }

        return true;
    }
}
