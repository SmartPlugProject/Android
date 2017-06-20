package eduardo.alexsander.com.smartplugandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eduardo.alexsander.com.smartplugandroid.R;
import eduardo.alexsander.com.smartplugandroid.model.Sensor;

/**
 * Created by 212571132 on 5/26/2017.
 */

public class SensorListAdapter extends RecyclerView.Adapter<SensorViewHolder> {
    private Context context;
    private List<Sensor> sensors;
    private OnSensorClickListener onSensorClickListener;


    public SensorListAdapter(Context context, List<Sensor> sensors, OnSensorClickListener onSensorClickListener) {
        this.context = context;
        this.sensors = sensors;
        this.onSensorClickListener = onSensorClickListener;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sensor_item, parent, false);
        SensorViewHolder holder = new SensorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, final int position) {
        Sensor s = sensors.get(position);
        holder.name.setText(s.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSensorClickListener.onSensorClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public interface OnSensorClickListener {
        void onSensorClick(int idx);
    }
}
