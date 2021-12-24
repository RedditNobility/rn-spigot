package me.kingtux.redditnobility;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum EntityHeads {
    DRAGON(1.0f, EntityType.ENDER_DRAGON, Material.DRAGON_HEAD),
    ZOMBIE(.10f, EntityType.ZOMBIE, Material.ZOMBIE_HEAD),
    SKELETON(.10f, EntityType.SKELETON, Material.SKELETON_SKULL),
    CREEPER(.10f, EntityType.CREEPER, Material.CREEPER_HEAD),
    SPIDER(.10f, EntityType.SPIDER,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==", "8bdb71d0-4724-48b2-9344-e79480424798"),
    WITCH(.10f, EntityType.WITCH,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=", "7f92b3d6-5ee0-4ab6-afae-2206b9514a63"),
    PILLAGER(.10f, EntityType.PILLAGER,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0=", "1ac9d5aa-46ef-4d71-b077-4564382c0a43"),

    SHEEP(.10f, EntityType.SHEEP,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19", "fa234925-9dbe-4b8f-a544-7c70fb6b6ac5"),
    PIG(.10f, EntityType.PIG,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=", "e1e1c2e4-1ed2-473d-bde2-3ec718535399"),
    CHICKEN(.10f, EntityType.CHICKEN,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==", "7d3a8ace-e045-4eba-ab71-71dbf525daf1"),

    COW(.10f, EntityType.COW,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19", "97ddf3b3-9dbe-4a3b-8a0f-1b19ddeac0bd");

    private final float chance;
    private final EntityType entity;
    private String base64;
    private String uuid;
    private Material head;

    EntityHeads(float chance, EntityType entity, String base64, String uuid) {
        this.chance = chance;
        this.entity = entity;
        this.base64 = base64;
        this.uuid = uuid;
    }

    EntityHeads(float chance, EntityType entity, Material head) {
        this.chance = chance;
        this.entity = entity;
        this.head = head;
    }

    public float getChance() {
        return chance;
    }

    public EntityType getEntity() {
        return entity;
    }

    public String getBase64() {
        return base64;
    }

    public Material getHead() {
        return head;
    }

    public ItemStack asItemStack() {
        ItemStack itemStack;
        if (head != null) {
            itemStack = new ItemStack(head);
        } else {
            itemStack = SkullUtils.getCustomTextureHead(base64, uuid);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(entity.getKey().getKey() + " Skull");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static EntityHeads getByType(EntityType type) {
        for (EntityHeads h : values()) {
            if (h.entity == type) return h;
        }
        return null;
    }
}
