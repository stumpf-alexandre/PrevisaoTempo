package com.stumpf.als.previsaotempo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PrevisaoTempoHttp {
    public static String URL;

    public static void setURL(String city){
        URL = "https://api.hgbrasil.com/weather/?format=json&city_name="+city+"&key=ee5f41af";
    }

    private static HttpURLConnection connectar(String urlWebservice){
        final int SEGUNDOS = 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        } catch (IOException e) {
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Temperature getTemperatureFromJson(JSONObject json){
        String date;
        String week;
        int max;
        int min;
        String description;
        Temperature temp = null;

        try {
            date = json.getString("date");
            week = json.getString("week");
            max = json.getInt("max");
            min = json.getInt("min");
            description = json.getString("descricao");

            temp = new Temperature(date, week, max, min, description);
        }catch (JSONException e){
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
        }
        return temp;
    }

    public static ArrayList<Temperature> readJsonTemperature(JSONObject json){
        ArrayList<Temperature> arrayList = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONArray forecast = results.getJSONArray("forecast");

            for (int i=0; i<forecast.length(); i++) {
                JSONObject a = forecast.getJSONObject(i);
                arrayList.add(getTemperatureFromJson(a));
            }
        }catch (JSONException e){
            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<Temperature> loadTemperature() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Temperature>  temperaturesList = readJsonTemperature(json);
                return temperaturesList;
            }

        } catch (Exception e) {
            Log.d(String.valueOf(R.string.validar_erro), e.getMessage());
        }
        return null;
    }

    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}