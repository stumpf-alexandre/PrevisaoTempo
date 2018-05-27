package com.stumpf.als.previsaotempo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TemperatureAdapter extends ArrayAdapter<Temperature> {
    public TemperatureAdapter(@NonNull Context context, @NonNull List<Temperature> object){
        super(context, 0, object);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        Temperature temp = getItem(position);
        View listTemperature = convertView;
        if (convertView == null){
            listTemperature = LayoutInflater.from(getContext()).inflate(R.layout.temperature_item, null);
        }
        TextView week = listTemperature.findViewById(R.id.txt_week);
        TextView date = listTemperature.findViewById(R.id.txt_date);
        TextView max = listTemperature.findViewById(R.id.txt_max);
        TextView min = listTemperature.findViewById(R.id.txt_min);
        TextView description = listTemperature.findViewById(R.id.txt_description);
        week.setText(temp.getWeek());
        date.setText(temp.getDate());
        max.setText(temp.getMax());
        min.setText(temp.getMin());
        description.setText(temp.getDescription());

        return listTemperature;
    }
}
