package me.kingtux.redditnobility;

import dev.nitrocommand.core.annotations.BaseCommand;
import dev.nitrocommand.core.annotations.NitroCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NitroCommand(command = "light", description = "Toggles Infinite Light on Lightable Blocks", format = "/light")
public class InfiniteLight implements Listener {
    private final List<UUID> infiniteLightUsers = new ArrayList<>();
    private RedditNobility redditNobility;

    public InfiniteLight(RedditNobility redditNobility) {
        this.redditNobility = redditNobility;
    }

    @BaseCommand
    public void init(Player player) {
        if (!player.hasPermission("rn.infinite-light")) {
            player.sendMessage(ChatColor.RED + "You lack permission");
            return;
        }
        if (infiniteLightUsers.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.GREEN + "Good News. You are no longer making items light forever");
            infiniteLightUsers.remove(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.GREEN + "Good News. You are now making items light forever!");
            infiniteLightUsers.add(player.getUniqueId());
        }

    }

    @EventHandler
    public void blockUpdate(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (block.getBlockData() instanceof Lightable lightable && block.hasMetadata("infinite-light")) {
            lightable.setLit(true);
            block.setBlockData(lightable);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getBlockData() instanceof Lightable lightable) {
            if (!infiniteLightUsers.contains(event.getPlayer().getUniqueId())) {
                return;
            }
            lightable.setLit(true);
            block.setMetadata("infinite-light", new FixedMetadataValue(redditNobility, true));
            block.setBlockData(lightable);
        }
    }
}
