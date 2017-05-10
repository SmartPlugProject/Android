package eduardo.alexsander.com.smartplugandroid;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 212571132 on 5/8/2017.
 */

public class SensorDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject content = json.getAsJsonObject().get("sensor").getAsJsonObject();
        Sensor sensor = new Gson().fromJson(content, typeOfT);
        JsonArray valueArray = content.getAsJsonObject().getAsJsonArray("value");
        Type type = new TypeToken<List<Sensor.Value>>(){}.getClass();
        Sensor.Value[] values = new Gson().fromJson(valueArray, Sensor.Value[].class);
        sensor.setValues(Arrays.asList(values));

        if (json.getAsJsonObject().get("sensors") != null) {
            //sensor = json.getAsJsonObject().get("sensors");
        }

        return sensor;
    }
}
