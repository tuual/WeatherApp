package ezgi.acar.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    Animation animation;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "Google'dan Bakılan Hava Durumu İle Aynı Olmayabilir. ", Toast.LENGTH_LONG).show();
        Bundle bundle = getIntent().getExtras();

        double feelsLike = bundle.getDouble("feels_like");
        int humidity = bundle.getInt("humidity");
        String icon = bundle.getString("icon");
        String wind = bundle.getString("wind");
        String clouds = bundle.getString("clouds");
        String cityName = bundle.getString("city");
        String description = bundle.getString("description");
        double temp = bundle.getDouble("temp");



       String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";

        Picasso.get().load(iconUrl).into(binding.imgWeather);
        Thread logoAnim = new Thread(){
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_anim);
                binding.imgWeather.startAnimation(animation);

            }
        };
        logoAnim.start();
        if (temp <= 12.0 ){
            binding.tvBilgi.setText(R.string.onikiaz);
        }
        else if(temp <= 18) {
            binding.tvBilgi.setText(R.string.onikiyuksek);

        }
        else if(temp <= 50.0){
            binding.tvBilgi.setText(R.string.onikidahayuksek);

        }

        binding.tvSicaklik.setText("Sıcaklık: " + decimalFormat.format(temp) + " °C");
        binding.tvSehir.setText(cityName.toUpperCase());
        binding.tvDurum.setText( description.toUpperCase());
        binding.tvNem.setText(humidity + "%");
        binding.tvRuzgarHizi.setText(wind + "m/s ");
        binding.tvBulutluluk.setText(clouds + "%");
        binding.tvHissedilen.setText("Hissedilen S: " + decimalFormat.format(feelsLike) + " °C");
    }




}