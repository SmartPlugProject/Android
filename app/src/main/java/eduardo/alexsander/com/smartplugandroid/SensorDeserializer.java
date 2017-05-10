package eduardo.alexsander.com.smartplugandroid;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 212571132 on 5/8/2017.
 */

public class SensorDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get("sensor");
        Sensor sensor = new Gson().fromJson(content, typeOfT);
        JsonElement values = content.getAsJsonObject().get("values");
        sensor.setValues(new Gson().fromJson(values, sensor.getValues().getClass()));

        if (json.getAsJsonObject().get("sensors") != null) {
            //sensor = json.getAsJsonObject().get("sensors");
        }

        return sensor;
    }
}
