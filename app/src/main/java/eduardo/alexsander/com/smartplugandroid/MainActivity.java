package eduardo.alexsander.com.smartplugandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CircleView mPowerConsumptionTextView;
    private CircleView mPriceTextView;
    private String id = "5903d2eb6cd39d2f84fcb58e";
    private float kwhPrice = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPowerConsumptionTextView = (CircleView) findViewById(R.id.power_consumption);
        mPriceTextView = (CircleView) findViewById(R.id.price);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Gson gson = new GsonBuilder().registerTypeAdapter(Sensor.class, new SensorDeserializer()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(SmartPlugApplication.API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SensorAPI sensorAPI = retrofit.create(SensorAPI.class);

        Call<Sensor> call = sensorAPI.searchSensorById(id);
        call.enqueue(new Callback<Sensor>() {
            @Override
            public void onResponse(Call<Sensor> call, Response<Sensor> response) {
                Sensor s = response.body();
                float powerConsumption = 0;
                if (s != null && s.getValues() != null) {
                    for (Sensor.Value v : s.getValues()) {
                        powerConsumption += (v.getVoltage() * v.getCurrent());
                    }
                }
                mPowerConsumptionTextView.setTitleText(String.format("%.2f kW", powerConsumption / 1000));
                mPriceTextView.setTitleText(String.format("R$ %.2f", powerConsumption * kwhPrice / 1000));
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {

            }
        });


    }
}
