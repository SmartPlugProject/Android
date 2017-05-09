package eduardo.alexsander.com.smartplugandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 212571132 on 5/8/2017.
 */

public interface SensorAPI {
    @GET("sensor/list")
    Call<List<Sensor>> getSensorList();

    @GET("sensor/searchById/{id}")
    Call<Sensor> searchSensorById(@Path("id") String id);
}
