package eduardo.alexsander.com.smartplugandroid;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by 212571132 on 5/8/2017.
 */

public class SensorDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement sensor = json.getAsJsonObject();

        if (json.getAsJsonObject().get("sensors") != null) {
            sensor = json.getAsJsonObject().get("sensors");
        }

        return (new Gson().fromJson(sensor, Sensor.class));
    }
}
