package eduardo.alexsander.com.smartplugandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by 212571132 on 6/5/2017.
 */

public class Value {
    @SerializedName("timestamp")
    public String date;
    public float voltage;
    public float current;
}
