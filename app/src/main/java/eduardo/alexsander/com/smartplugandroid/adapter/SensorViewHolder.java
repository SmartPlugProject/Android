package eduardo.alexsander.com.smartplugandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eduardo.alexsander.com.smartplugandroid.R;

/**
 * Created by 212571132 on 5/26/2017.
 */

public class SensorViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView name;

    public SensorViewHolder(View itemView) {
        super(itemView);

        icon = (ImageView) itemView.findViewById(R.id.sensor_icon);
        name = (TextView) itemView.findViewById(R.id.sensor_name);
    }
}
