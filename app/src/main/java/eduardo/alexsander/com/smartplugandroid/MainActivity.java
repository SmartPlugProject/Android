package eduardo.alexsander.com.smartplugandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mPowerConsumptionTextView;
    private TextView mPriceTextView;
    private String id = "5903d2eb6cd39d2f84fcb58e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPowerConsumptionTextView = (TextView) findViewById(R.id.power_consumption);
        mPriceTextView = (TextView) findViewById(R.id.price);
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
                if (s != null) {
                    for (Sensor.Value v : s.getValues()) {
                        powerConsumption += (v.getVoltage() * v.getCurrent());
                    }
                    mPowerConsumptionTextView.setText(String.format("%f W", powerConsumption));
                }
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {

            }
        });


    }
}
