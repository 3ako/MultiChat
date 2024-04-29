package hw.zako.multichat.redis.packet.list;

import com.google.gson.*;
import hw.zako.multichat.redis.packet.AbstractRedisPacket;
import lombok.Getter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.Nullable;

@Getter
@Log
public class ChatMessagePacket extends AbstractRedisPacket {
    private String username;
    private String message;

    public ChatMessagePacket(String username, String message) {
        super(null);
        this.username = username;
        this.message = message;
    }

    public ChatMessagePacket() {
        this(null);
    }

    public ChatMessagePacket(@Nullable String source) {
        super(source);
    }

    @Override
    public void read() {
        try {
            final JsonElement element = new JsonParser().parse(getSource());
            if (!element.isJsonObject()) return;
            final JsonObject object = element.getAsJsonObject();

            message = object.get("message").getAsString();
            username = object.get("username").getAsString();
        } catch (JsonSyntaxException e) {
            log.warning("Packet parse error: "+ e.getMessage());
        }
    }

    @Override
    public void write() {
        JsonObject jo = new JsonObject();
        jo.add("message", new JsonPrimitive(message));
        jo.add("username", new JsonPrimitive(username));
        this.setSource(jo.toString());
    }
}
