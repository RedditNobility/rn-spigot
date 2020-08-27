package me.kingtux.redditroyalty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class EntityListeners implements Listener {


    @EventHandler
    private void death(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            meta.setPlayerProfile(Bukkit.createProfile(e.getEntity().getUniqueId()));
            itemStack.setItemMeta(meta);
            e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
        } else {
            EntityHeads heads = EntityHeads.getByType(e.getEntityType());
            if (heads == null) return;
            double chance = Math.random();
            if (chance <= heads.getChance()) {
                e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), heads.asItemStack());
            }
        }
    }

    @EventHandler
    private void life(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.BAT) {
            e.setCancelled(true);
            if (e.getEntity() instanceof Damageable) {
                ((Damageable) e.getEntity()).damage(100);
            }

        }
    }
}
