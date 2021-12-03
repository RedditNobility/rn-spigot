package me.kingtux.redditroyalty.auth;


import me.kingtux.tuxorm.BasicLoggingObject;
import me.kingtux.tuxorm.annotations.DBTable;
import me.kingtux.tuxorm.annotations.TableColumn;

@DBTable(name = "users")
public class AuthModel extends BasicLoggingObject {
    @TableColumn
    private String mcUser;
    @TableColumn
    private AuthStatus status;
    @TableColumn
    private String redditUsername;

    public AuthModel(String mcUser, AuthStatus status, String redditUsername) {
        this.mcUser = mcUser;
        this.status = status;
        this.redditUsername = redditUsername;
    }

    public AuthModel() {

    }

    public String getMcUser() {
        return mcUser;
    }

    public AuthStatus getStatus() {
        return status;
    }

    public String getRedditUsername() {
        return redditUsername;
    }
}