package me.kingtux.redditroyalty;

import dev.nitrocommand.bukkit.BukkitCommandCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class RedditRoyalty extends JavaPlugin {
    private BukkitCommandCore bukkitCommandCore;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new BlockListeners(this), this);
        getServer().getPluginManager().registerEvents(new EntityListeners(), this);
        loadSpawners();
        bukkitCommandCore = new BukkitCommandCore(this);

    }

    private void loadSpawners() {
        getServer().addRecipe(createSpawnerRecipe(EntityType.CHICKEN, Material.EGG));
        getServer().addRecipe(createSpawnerRecipe(EntityType.PIG, Material.PORKCHOP));
        getServer().addRecipe(createSpawnerRecipe(EntityType.SKELETON, Material.BONE));
        getServer().addRecipe(createSpawnerRecipe(EntityType.ZOMBIE, Material.ROTTEN_FLESH));
        getServer().addRecipe(createSpawnerRecipe(EntityType.ENDERMAN, Material.ENDER_PEARL));
        getServer().addRecipe(createSpawnerRecipe(EntityType.BLAZE, Material.BLAZE_ROD));
        getServer().addRecipe(createSpawnerRecipe(EntityType.CREEPER, Material.GUNPOWDER));
        getServer().addRecipe(createSpawnerRecipe(EntityType.SPIDER, Material.STRING));
        getServer().addRecipe(createSpawnerRecipe(EntityType.RABBIT, Material.RABBIT));
        getServer().addRecipe(createSpawnerRecipe(EntityType.SHEEP, Material.WHITE_WOOL));
        getServer().addRecipe(createSpawnerRecipe(EntityType.COW, Material.BEEF));
        getServer().addRecipe(createSpawnerRecipe(EntityType.MUSHROOM_COW, Material.SUSPICIOUS_STEW));

        //TODO bring back our friend!
        getServer().addRecipe(createSpawnerRecipe(EntityType.ZOMBIFIED_PIGLIN, Material.GOLDEN_SWORD));
        getServer().addRecipe(createSpawnerRecipe(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SKULL));
        getServer().addRecipe(createSpawnerRecipe(EntityType.VILLAGER, Material.TOTEM_OF_UNDYING));
        getServer().addRecipe(createSpawnerRecipe(EntityType.ZOMBIE_VILLAGER, Material.EMERALD));
        getServer().addRecipe(createSpawnerRecipe(EntityType.SLIME, Material.SLIME_BALL));
        getServer().addRecipe(createSpawnerRecipe(EntityType.WITCH, Material.GLOWSTONE_DUST));
        getServer().addRecipe(createSpawnerRecipe(EntityType.DROWNED, Material.HEART_OF_THE_SEA));

    }

    public ShapedRecipe createSpawnerRecipe(EntityType type, Material core) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, type.getKey().getKey().toLowerCase() + "_spawner")
                , createSpawner(type));
        recipe.shape("III", "ISI", "III");
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('S', core);
        return recipe;
    }

    public static ItemStack createSpawner(EntityType type) {
        ItemStack spawner = new ItemStack(Material.SPAWNER, 1);
        ItemMeta itemMeta = spawner.getItemMeta();
        itemMeta.setDisplayName(type.getKey().getKey() + " Spawner");

        spawner.setItemMeta(itemMeta);
        BlockStateMeta bsm = (BlockStateMeta) spawner.getItemMeta();
        CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
        cs.setRequiredPlayerRange(0);
        cs.setSpawnedType(type);
        bsm.setBlockState(cs);
        spawner.setItemMeta(bsm);
        return spawner;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
