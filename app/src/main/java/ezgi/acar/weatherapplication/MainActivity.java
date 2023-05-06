package ezgi.acar.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import ezgi.acar.weatherapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String seciliSehir;
    private final String appid = "f089bdc42270c5c7e23b30d776fd72cf";
    private final String url = "http://api.openweathermap.org/data/2.5/weather";
    private String sehir,ulke;
    private String tempUrl = "";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    binding.btnGoster.setOnClickListener(view ->{
        getWeather();
    });

    }


    public void getWeather(){

        sehir = binding.etSehir.getText().toString().trim();
        ulke = binding.etUlke.getText().toString().trim();
        String lang = "tr";

        if (sehir.equals("")){
            Toast.makeText(this, " Şehir Boş Bırakılamaz !", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!ulke.equals("")){
                tempUrl = url +"&lang=" + lang+ "?q=" + sehir + "," + ulke + "&appid=" + appid ;
            }
            else{
                tempUrl = url +  "?q=" + sehir +"&appid=" + appid+ "&lang=" + lang ;


            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    JSONObject jsonObject = null;
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        String icon = jsonObjectWeather.getString("icon");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                        Picasso.get().load(iconUrl).into(binding.imgWeather);
                        binding.tvSicaklik.setText("Sıcaklık: " + decimalFormat.format(temp) + " °C");
                        binding.tvSehir.setText(cityName.toUpperCase());
                        binding.tvDurum.setText( description.toUpperCase());
                        binding.tvNem.setText(humidity + "%");
                        binding.tvRuzgarHizi.setText(wind + "m/s ");
                        binding.tvBulutluluk.setText(clouds + "%");




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }

        });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }

}