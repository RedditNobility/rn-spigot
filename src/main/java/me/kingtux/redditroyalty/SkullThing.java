package me.kingtux.redditroyalty;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullThing {
    public static ItemStack getCustomTextureHead(String base64, String uuid) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.fromString(uuid));
        profile.setProperty(new ProfileProperty("textures", base64));
        meta.setPlayerProfile(profile);
        head.setItemMeta(meta);
        return head;
    }
}
