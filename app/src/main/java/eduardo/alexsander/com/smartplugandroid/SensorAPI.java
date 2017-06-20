package eduardo.alexsander.com.smartplugandroid;

import java.util.List;

import eduardo.alexsander.com.smartplugandroid.model.Sensor;
import eduardo.alexsander.com.smartplugandroid.model.SensorList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 212571132 on 5/8/2017.
 */

public interface SensorAPI {
    String BASE_URL = "http://smart-plug-1.herokuapp.com/sensor/";

    @GET("list")
    Call<SensorList> getSensorList();

    @GET("searchById/{id}")
    Call<Sensor> getSensorById(@Path("id") String id);
}
