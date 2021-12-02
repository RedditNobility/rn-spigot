package me.kingtux.redditroyalty.auth;

import me.kingtux.redditroyalty.RedditRoyalty;
import me.kingtux.tuxorm.Dao;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class AuthManager implements Listener {
    private List<UUID> validUsers = new ArrayList<>();
    private RedditRoyalty redditRoyalty;
    private Dao<AuthModel, Long> authDao;

    public AuthManager(RedditRoyalty redditRoyalty) {
        this.redditRoyalty = redditRoyalty;
        authDao = redditRoyalty.getConnection().createDao(AuthModel.class);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (validUsers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        Optional<AuthModel> mcUser = authDao.fetchFirst("mcUser", event.getPlayer().getUniqueId().toString());
        if (mcUser.isEmpty()) {
            event.getPlayer().sendMessage("Please Register Via /auth login {RedditUsername}");
            event.setCancelled(true);
            return;
        }
        validUsers.add(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        validUsers.remove(event.getPlayer().getUniqueId());
    }

    public List<UUID> getValidUsers() {
        return validUsers;
    }

    public RedditRoyalty getRedditRoyalty() {
        return redditRoyalty;
    }
    public Dao<AuthModel, Long> getAuthDao() {
        return authDao;
    }
}
