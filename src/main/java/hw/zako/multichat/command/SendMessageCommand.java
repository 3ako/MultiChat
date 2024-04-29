package hw.zako.multichat.command;

import hw.zako.multichat.redis.RedisManager;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class SendMessageCommand implements CommandExecutor {
    private final RedisManager redisManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return false;

        StringBuilder sb = new StringBuilder();
        for (String arg: args) {
            sb.append(arg)
                    .append(" ");
        }

        redisManager.sendMessage(sender.getName(), sb.toString());
        return true;
    }
}
