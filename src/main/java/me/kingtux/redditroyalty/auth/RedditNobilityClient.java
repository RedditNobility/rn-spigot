package me.kingtux.redditroyalty.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Optional;

public class RedditNobilityClient {
    private final OkHttpClient client;
    private AuthToken authToken;
    private Gson gson = new Gson();
    private final String userAgent = "Reddit Nobility MC Server";

    public RedditNobilityClient(String username, String password) throws IllegalArgumentException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://redditnobility.org/api/login/password").header("User-Agent", userAgent).post(RequestBody.create(gson.toJson(new LoginRequest(username, password)), MediaType.get("application/json; charset=utf-8”")))
                .build();
        try (Response execute = client.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                String body = execute.body().string();
                BasicResponse<AuthToken> response = gson.fromJson(body, TypeToken.getParameterized(BasicResponse.class, AuthToken.class).getType());
                if (!response.isSuccess()) {
                    throw new IllegalArgumentException("Invalid Auth Details");
                }
                authToken = response.getData().get();
            } else {
                throw new IllegalArgumentException("Invalid Auth Details");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Auth Details", e);
        }
    }

    public boolean isValid(String username) {
        Request request = new Request.Builder()
                .url("https://redditnobility.org/api/login/password").header("User-Agent", userAgent).header("Authorization", "Bearer " + authToken.getToken())
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
    private Optional<T> data;

    public boolean isSuccess() {
        return success;
    }

    public Optional<T> getData() {
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