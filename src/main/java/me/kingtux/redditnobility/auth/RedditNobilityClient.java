package me.kingtux.redditnobility.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;

public class RedditNobilityClient {
    private OkHttpClient client;
    private AuthToken authToken;
    private Gson gson = new Gson();
    private String userAgent = "Reddit Nobility MC Server";
    private String username;
    private String password;

    public RedditNobilityClient(String username, String password) throws IllegalArgumentException {
        client = new OkHttpClient();

        this.username = username;
        this.password = password;
        loadToken();
    }

    private void loadToken() {
        JsonObject object = new JsonObject();
        object.addProperty("username", username);
        object.addProperty("password", password);
        Request request = new Request.Builder()
                .url("https://redditnobility.org/api/login/password").header("User-Agent", userAgent).post(RequestBody.create(gson.toJson(object), MediaType.parse("application/json; charset=utf-8")))
                .build();
        try (Response execute = client.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                String body = execute.body().string();
                BasicResponse<AuthToken> response = gson.fromJson(body, TypeToken.getParameterized(BasicResponse.class, AuthToken.class).getType());
                if (!response.isSuccess()) {
                    throw new IllegalArgumentException("Invalid Auth Details");
                }
                authToken = response.getData();
            } else {
                throw new IllegalArgumentException("Invalid Auth Details");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Auth Details", e);
        }
    }

    public boolean isValid(String username) {
        Request request = new Request.Builder()
                .url("https://redditnobility.org/moderator/user/"+username).header("User-Agent", userAgent).header("Authorization", "Bearer " + authToken.getToken())
                .build();
        try (Response execute = client.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                String body = execute.body().string();
                BasicResponse<User> response = gson.fromJson(body, TypeToken.getParameterized(BasicResponse.class, User.class).getType());
                if (!response.isSuccess()) {
                    return false;
                }
                return true;
            } else {
                if (execute.code() == 404) {
                    return false;
                } else if (execute.code() == 401) {
                    loadToken();
                    // Stackoverflow go brrrrrrr
                    isValid(username);
                }
                throw new IllegalArgumentException("Bad Request: " + execute.code());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Auth Details", e);
        }
    }
}

class BasicResponse<T> {
    private boolean success;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}

class AuthToken {
    private long user;
    private String token;

    public long getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }


}

class User {
    private long id;
    private String username;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}