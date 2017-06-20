package eduardo.alexsander.com.smartplugandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 212571132 on 6/5/2017.
 */

public class Sensor {
    @SerializedName("_id")
    public String id;
    public String name;
    public String device;
    public List<Value> values;
}
