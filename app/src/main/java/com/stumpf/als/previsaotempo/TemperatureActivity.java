package com.stumpf.als.previsaotempo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class TemperatureActivity extends AppCompatActivity {
    TemperatureTask task;
    ArrayList<Temperature> temp;
    ListView listTemperature;
    TemperatureAdapter adapter;
    public static String cit;
    EditText city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        city = findViewById(R.id.txt_city);
        Button btn = findViewById(R.id.btn_pesquisa);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city.getText().length() != 0) {
                    cit = city.getText().toString();
                    PrevisaoTempoHttp.setURL(cit);
                    listTemperature = findViewById(R.id.list_temp);
                    search();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.validar_cidade), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void search(){
        if(temp == null) {
            temp = new ArrayList<Temperature>();
        }
        adapter = new TemperatureAdapter(getApplicationContext(), temp);
        listTemperature.setAdapter(adapter);
        startDownload();
        if (task == null) {
            if (PrevisaoTempoHttp.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.validar_conexao), Toast.LENGTH_LONG).show();
            }
        }
        else if (task.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(), getString(R.string.validar_busca),Toast.LENGTH_LONG).show();
        }
    }

    public void startDownload(){
        if (task == null || task.getStatus() != AsyncTask.Status.RUNNING){
            task = new TemperatureTask();
            task.execute();
        }
    }

    class TemperatureTask extends AsyncTask<Void, Void, ArrayList<Temperature>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),getString(R.string.validar_busca) , Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Temperature> doInBackground(Void... strings) {
            ArrayList<Temperature> temp = PrevisaoTempoHttp.loadTemperature();
            return temp;
        }
        @Override
        protected void onPostExecute(ArrayList<Temperature> clima) {
            super.onPostExecute(clima);
            if (clima != null) {
                temp.clear();
                temp.addAll(clima);
                adapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), getString(R.string.validar_busca), Toast.LENGTH_LONG).show();
            }
        }
    }
}