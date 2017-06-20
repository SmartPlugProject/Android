package eduardo.alexsander.com.smartplugandroid;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import eduardo.alexsander.com.smartplugandroid.model.Sensor;
import eduardo.alexsander.com.smartplugandroid.model.Value;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CircleView mPowerConsumptionTextView;
    private LineChart energyChart;
    private float kwhPrice = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = getIntent().getStringExtra("id");
        Log.d("MainActivity", "id: " + id);

        mPowerConsumptionTextView = (CircleView) findViewById(R.id.power_consumption);
        energyChart = (LineChart) findViewById(R.id.energy_chart);
        energyChart.getDescription().setEnabled(false);
        energyChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        energyChart.getXAxis().setDrawGridLines(false);
        YAxis rightAxis = energyChart.getAxisRight();
        rightAxis.setEnabled(false);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SensorAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SensorAPI api = retrofit.create(SensorAPI.class);

        Call<Sensor> call = api.getSensorById(id);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        call.enqueue(new Callback<Sensor>() {
            @Override
            public void onResponse(Call<Sensor> call, Response<Sensor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Sensor s = response.body();
                    float energy = 0;
                    List<Entry> entries = new ArrayList<Entry>();
                    for (Value v : s.values) {
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                        try {
                            Date date = formatter.parse(v.date);
                            Log.d("TAG", "Timestamp ms: " + date.getTime());

                            entries.add(new Entry(date.getMinutes(), v.voltage * v.current * 0.005f));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        float power = v.voltage * v.current;
                        energy += power * 5E-3;

                    }
                    progressDialog.dismiss();
                    LineDataSet dataSet = new LineDataSet(entries, "Energia");
                    dataSet.setColor(R.color.colorPrimary);
                    dataSet.setValueTextColor(R.color.colorPrimaryDark);

                    LineData data = new LineData(dataSet);
                    energyChart.setData(data);
                    energyChart.invalidate();
                    mPowerConsumptionTextView.setTitleText("R$ " + energy * kwhPrice);
                }
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
