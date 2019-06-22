package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.weatherAPI.Weather;
import com.example.myapplication.weatherAPI.WeatherMain;
import com.example.myapplication.weatherAPI.WeatherResponse;
import com.example.myapplication.weatherAPI.WeatherService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Locale.getDefault;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView txt1, txt2, txt3, txt4;
    ProgressBar bb ;
    Geocoder geocoder;
String weatherAPI = "9b2e60b57f5538a6ae2724f3aa428107" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txt1 = (TextView) findViewById(R.id.firstTxt);
        txt2 = (TextView) findViewById(R.id.secondTxt);
        txt3 = (TextView) findViewById(R.id.thirdTxt);

        txt4 = (TextView) findViewById(R.id.forthTxt);
        geocoder = new Geocoder(this, getDefault());
bb = (ProgressBar)findViewById(R.id.bb);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng palestine = new LatLng(32, 35);
        mMap.addMarker(new MarkerOptions().position(palestine).title("PALESTINE"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(palestine));

        //addresses = null ;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {


            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.clear();


                double x = latLng.latitude;
                double y = latLng.longitude;
                LatLng city = new LatLng(x, y);



                mMap.addMarker(new MarkerOptions().position(city));

                mMap.animateCamera(CameraUpdateFactory.newLatLng(city));
                txt1.setText(x + "               " + y);
                getCity(x, y);

                Log.d("hell" , x + "               " + y);
                fetchWeather( Double.toString(x) , Double.toString(y));


            }


        });


    }

    private void getCity(double x, double y) {
        try {
            Geocoder geocoder;
            geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(x, y, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                Log.d("fuck", address);
                if(address!=null)
                 txt4.setText(address);
                else
                    txt4.setText("UNKNOWN");



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void fetchWeather(String lat  , String lon) {

//Log.d("latlon" , lat + " "+lon);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
//////
        //Run the Request :


        //don't use execute  .. we don't wanna run
        // it ASYNC . . .
        bb.setVisibility(View.VISIBLE);
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        txt4.setVisibility(View.GONE);

        service.get("9b2e60b57f5538a6ae2724f3aa428107",lat,lon).enqueue(new Callback<WeatherResponse>() {

             @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                 bb.setVisibility(View.GONE);
                 txt1.setVisibility(View.VISIBLE);
                 txt2.setVisibility(View.VISIBLE);
                 txt3.setVisibility(View.VISIBLE);
                 txt4.setVisibility(View.VISIBLE);

                 Log.d("dd" , response.body().toString()) ;
               String jsonData = response.body().toString();

               Log.d("sad", jsonData) ;
                 if (response.isSuccessful()) {
                     //  Log.v("sad", response.body().toString());
                     //Gson g = new Gson();
                     //WeatherResponse p = g.fromJson(jsonData, WeatherResponse.class);
                    double xc=  response.body().getmMain().getmTemp();
                    String d = Double.toString(xc) ;
                    String x =  response.body().getmWeather().get(0).getmDescription();

                    txt2.setText( d);
                     txt3.setText( x  );



                 }


            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
//Log.d("hell" , "fuck");
            }

            // GitHubService service = retrofit.create(GitHubService.class);
        });


    }
}
