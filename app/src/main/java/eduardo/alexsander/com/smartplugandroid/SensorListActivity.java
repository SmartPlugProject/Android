package eduardo.alexsander.com.smartplugandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import eduardo.alexsander.com.smartplugandroid.adapter.SensorListAdapter;
import eduardo.alexsander.com.smartplugandroid.model.Sensor;
import eduardo.alexsander.com.smartplugandroid.model.SensorList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SensorListActivity extends AppCompatActivity {

    private RecyclerView rvSensors;
    private SensorListAdapter adapter;
    private SensorList sensorList;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        rootView = findViewById(R.id.sensor_list_root_view);

        rvSensors = (RecyclerView) findViewById(R.id.rv_sensors);
        rvSensors.setHasFixedSize(true);
        rvSensors.setLayoutManager(new LinearLayoutManager(this));
        rvSensors.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SensorAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SensorAPI api = retrofit.create(SensorAPI.class);
        Call<SensorList> call = api.getSensorList();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        call.enqueue(new Callback<SensorList>() {
            @Override
            public void onResponse(Call<SensorList> call, Response<SensorList> response) {
                sensorList = response.body();
                adapter = new SensorListAdapter(SensorListActivity.this, sensorList.sensors, onSensorClick());
                rvSensors.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SensorList> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(rootView, "Sem conexão...", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private SensorListAdapter.OnSensorClickListener onSensorClick() {
        return new SensorListAdapter.OnSensorClickListener() {
            @Override
            public void onSensorClick(int idx) {
                Sensor sensorClicked = sensorList.sensors.get(idx);
                Intent intent = new Intent(SensorListActivity.this, MainActivity.class);
                intent.putExtra("id", sensorClicked.id);
                startActivity(intent);
            }
        };
    }
}
