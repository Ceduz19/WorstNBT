package me.ceduz19.worstnbt.plugin;

import me.ceduz19.worstnbt.core.NBTCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class WorstNBTPlugin extends JavaPlugin {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInHand();
        NBTCompound compound = NBTCompound.fromItemStack(item);

        compound.putLongArray("test", new long[] {0, 1, 2, 999});
        getLogger().info(Arrays.toString(compound.getLongArray("test")));
        getLogger().info(compound.toString());
        return true;
    }
}
