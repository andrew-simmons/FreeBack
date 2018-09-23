package hackthemachine.ai.team_free.json;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.UUID;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResponderReader {
	public static Collection<Responder> read(InputStream is) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(new InputStreamReader(is));
		return parse(element);
	}

	public static Collection<Responder> read(String str) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		return parse(element);
	}

	public static Collection<Responder> parse(JsonElement element) {
		Map<Long, Responder> responders = new TreeMap<>();

		if (element.isJsonObject()) {
			JsonObject root = element.getAsJsonObject();
			parseJson(root.get("evidence_id").getAsJsonObject(), responders, (r, e) -> r.evidenceId = UUID.fromString(e.getAsString()));
			parseJson(root.get("title").getAsJsonObject(), responders, (r, e) -> r.streetAddress = e.getAsString());
			parseJson(root.get("duration_seconds").getAsJsonObject(), responders, (r, e) -> r.duration = Double.parseDouble(e.getAsString()));
			parseJson(root.get("location").getAsJsonObject(), responders, (r, e) -> r.location = e.getAsString());
			parseJson(root.get("latitude").getAsJsonObject(), responders, (r, e) -> r.latitude = Double.parseDouble(e.getAsString()));
			parseJson(root.get("longitude").getAsJsonObject(), responders, (r, e) -> r.longitude = Double.parseDouble(e.getAsString()));
			parseJson(root.get("Responder Type").getAsJsonObject(), responders, (r, e) -> r.type = e.getAsString());
		}

		return responders.values();
	}

	public static void parseJson(JsonObject object, Map<Long, Responder> responders, BiConsumer<Responder, JsonElement> setter) {
		for (Map.Entry<String, JsonElement> field : object.entrySet()) {
			final long id = Long.parseLong(field.getKey());
			final JsonElement value = field.getValue();

			if (!responders.containsKey(id)) {
				responders.put(id, new Responder(id));
			}
			setter.accept(responders.get(id), value);
		}
	}


}