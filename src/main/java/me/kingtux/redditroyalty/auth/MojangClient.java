package me.kingtux.redditroyalty.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class MojangClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private static final String userAgent = "Reddit Nobility MC Server";


    public static Optional<UUID> getUUID(String username) {
        Request request = new Request.Builder()
                .url("https://api.mojang.com/users/profiles/minecraft/" + username).header("User-Agent", userAgent)
                .build();
        try (Response execute = client.newCall(request).execute()) {
            if (execute.isSuccessful()) {
                String body = execute.body().string();
                JsonObject response = gson.fromJson(body, JsonObject.class);

                StringBuffer id = new StringBuffer(response.get("id").getAsString());
                id.insert(20, '-');
                id.insert(16, '-');
                id.insert(12, '-');
                id.insert(8, '-');
                return Optional.of(UUID.fromString(id.toString()));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Auth Details", e);
        }
    }
}
