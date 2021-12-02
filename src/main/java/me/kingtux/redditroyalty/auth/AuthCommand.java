package me.kingtux.redditroyalty.auth;

import dev.nitrocommand.bukkit.annotations.BukkitPermission;
import dev.nitrocommand.core.annotations.BaseCommand;
import dev.nitrocommand.core.annotations.CommandArgument;
import dev.nitrocommand.core.annotations.NitroCommand;
import dev.nitrocommand.core.annotations.SubCommand;
import me.kingtux.redditroyalty.RedditRoyalty;
import me.kingtux.tuxcore.TuxCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@NitroCommand(command = "auth", description = "Auth Tool For RedditNobility ", format = "/auth login {RedditUsername}")
public class AuthCommand {
    private RedditRoyalty redditRoyalty;
    private AuthManager authManager;

    public AuthCommand(RedditRoyalty redditRoyalty, AuthManager authManager) {
        this.redditRoyalty = redditRoyalty;
        this.authManager = authManager;
    }

    @BaseCommand
    public void init() {

    }

    @SubCommand(format = "login {RedditUsername}")
    public void login(Player sender, @CommandArgument("RedditUsername") String redditUsername) {

    }

    @SubCommand(format = "friend {mcUsername}")
    public void addFriend(CommandSender sender, @CommandArgument("mcUsername") String mcUsername) {

    }

    @SubCommand(format = "classicRoyal {mcUsername}")
    @BukkitPermission("rn.admin")
    public void addClassicRoyal(CommandSender sender, @CommandArgument("mcUsername") String mcUsername) {

    }
}