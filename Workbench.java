package workbench.workbench;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Workbench extends JavaPlugin  implements CommandExecutor {
    PluginDescriptionFile descriptionFile = getDescription();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lEnabling &9&l[&a&lWorkbench&e&l+&9&l] &a&l(version: " + descriptionFile.getVersion() + ")"));
        registerCommands();
        registerConfig();
    }

    public void registerCommands() {
        getCommand("wb").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration fileConfiguration = getConfig();
        String Path = "Config.enabled";
        if (sender instanceof Player) {
            if (fileConfiguration.getString(Path).equals("true")) {
                if (args.length > 0) {
                    if (args[0].equals("workbench")) {
                        if (sender.hasPermission("workbench.inventory") || sender.isOp()) {
                            ((Player) sender).openWorkbench(((Player) sender).getLocation(), true);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to execute this command!"));
                            return false;
                        }
                    } else if (args[0].equals("enchantment")) {
                        if (sender.hasPermission("workbench.inventory") || sender.isOp()) {
                            ((Player) sender).openEnchanting(((Player) sender).getLocation(), true);
                            return true;
                        }
                    } else if (args[0].equals("reload")) {
                        if (sender.hasPermission("workbench.reload") || sender.isOp()) {
                            reloadConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[&a&lWorkbench&e&l+&9&l] &f&lThe plugin was reloaded successfully"));
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to execute this command!"));
                            return false;
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis command doesn't exist &f&l%player%!").replaceAll("%player%", sender.getName()));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis command doesn't exist &f&l%player%!").replaceAll("%player%", sender.getName()));
                    return false;
                }
            } else if (fileConfiguration.getString(Path).equals("false")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe plugin is disabled &e&l%player%!").replaceAll("%player%", sender.getName()));
                return false;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAn exception on the file &f&lconfig.yml %player%!").replaceAll("%player%", sender.getName()));
                return false;
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis command cannot be executed from the console!"));
        }
        return false;
    }

    public void registerConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lDisabling &9&l[&a&lWorkbench&e&l+&9&l] &a&l(version: " + descriptionFile.getVersion() + ")"));
    }
}
