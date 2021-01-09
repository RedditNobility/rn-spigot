package me.kingtux.redditroyalty;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class BlockListeners implements Listener {
    private RedditRoyalty redditRoyalty;

    public BlockListeners(RedditRoyalty redditRoyalty) {
        this.redditRoyalty = redditRoyalty;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SPAWNER) {

            CreatureSpawner cs = (CreatureSpawner) event.getBlock().getState();
            EntityType spawnedEntity = cs.getSpawnedType();

            event.getBlock().getLocation().getWorld().
                    dropItem(event.getBlock().getLocation().add(0.5, 0, 0.5), RedditRoyalty.createSpawner(spawnedEntity));

            event.setExpToDrop(0);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SPAWNER) {
            ItemStack spawner = event.getItemInHand();
            BlockStateMeta bsm = (BlockStateMeta) spawner.getItemMeta();
            CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
            EntityType type = cs.getSpawnedType();
            CreatureSpawner s = (CreatureSpawner) event.getBlock().getState();
            s.setRequiredPlayerRange(-1);

            s.setSpawnedType(type);
            s.update();
        }
    }
}
