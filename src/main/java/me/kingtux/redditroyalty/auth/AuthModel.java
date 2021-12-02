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

}