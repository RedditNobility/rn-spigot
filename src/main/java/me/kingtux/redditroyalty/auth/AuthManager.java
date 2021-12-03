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
    protected RedditNobilityClient nobilityClient;

    public AuthManager(RedditRoyalty redditRoyalty) {
        this.redditRoyalty = redditRoyalty;
        nobilityClient = new RedditNobilityClient(redditRoyalty.getConfig().getString("site.username"), redditRoyalty.getConfig().getString("site.password"));
        authDao = redditRoyalty.getConnection().createDao(AuthModel.class);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!isLoggedIn(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage("Please register via /auth login {reddit_username}");
            event.getPlayer().sendMessage("If you believe this is a mistake please contact KingTux#0042");
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        validUsers.remove(event.getPlayer().getUniqueId());
    }

    public boolean isLoggedIn(UUID id) {
        if (validUsers.contains(id)) {
            return true;
        }
        Optional<AuthModel> mcUser = authDao.fetchFirst("mcUser", id.toString());
        if (mcUser.isEmpty()) {
            return false;
        }
        validUsers.add(id);
        return true;
    }

    public Optional<AuthModel> getRedditUsername(UUID id) {
        return authDao.fetchFirst("mcUser", id.toString());
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
