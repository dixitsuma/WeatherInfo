package com.example.user3.weatherinfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        MyRecyclerViewAdapter.OnItemClicked,GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemSelectedListener {

    public boolean gotWeather=false;
    public boolean gotForecast=false;
    List<Forecast_Wholeday> wholedays=new ArrayList<>();
    public RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private LayoutInflater inflater;
    private Context context;
    private GoogleMap googleMap;
    private UiSettings mUiSettings;
    protected LocationManager locationManager;
    public boolean isLocationSet = false;
    public boolean isGpsEnabled;
    private Map<String,FinalWeatherData> marker_map = new HashMap<>();
    List<LatLng> markers = new ArrayList<>();

    ImageButton clear;
    TextView  desc,temp,sun_rise,sun_set,press,rain;
    ImageView icon;
    RelativeLayout bottombar;
    RelativeLayout layoutBottomSheet;
    ProgressBar progressBar;

    BottomSheetBehavior sheetBehavior;

    //  BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutBottomSheet=findViewById(R.id.recycler_layout);
        icon =  findViewById(R.id.iv_icon_image);
        temp = findViewById(R.id.tv_temperature);
        press = findViewById(R.id.tv_pressure);
        desc = findViewById(R.id.tv_desc);
        sun_rise = findViewById(R.id.tv_sunrise);
        sun_set = findViewById(R.id.tv_sunset);
        clear= findViewById(R.id.ib_clear);

         progressBar=findViewById(R.id.progress);
         progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);


        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter = new MyRecyclerViewAdapter(this, wholedays);
        recyclerView.setAdapter(adapter);
//        adapter.setOnClick(this);

        bottombar = findViewById(R.id.recycler_layout);
        //   multi_marker = (CheckBox) findViewById(R.id.cb_multimarker);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isGpsEnabled = true;
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

        Spinner spinner=findViewById(R.id.spinner_map_type);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.map_type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.clear();
                markers.clear();
                desc.setText("click a marker to get weather");

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);

        MapStyleOptions style=MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json);
        googleMap.setMapStyle(style);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setPadding(0,30,0,160);
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);

        mUiSettings = googleMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MapsActivity.this, "Location button clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(MapsActivity.this,"Locadton= "+location,Toast.LENGTH_LONG).show();
            }
        });

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng latLng) {
                progressBar.setVisibility(View.VISIBLE);
                   createMarker(latLng);
                // Setting the title for the marker.
                // This will be displayed on taping the marker

     /*           googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        inflater=(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                        View v = inflater.inflate(R.layout.info_window, null);

                        FinalWeatherData data=marker_map.get(marker.getId());
                        ExtraMain resp=data.getExtraMain();
                        Forecast_Wholeday forecast_wholeday=data.getForecast_wholeday();
                        Log.d("FORECAST WHOLE DAY: ",forecast_wholeday.toString());
                        int temperature=(int) (resp.getMain().getTemp()-273.15);

                        TextView title = v.findViewById(R.id.title);
                        title.setText(String.valueOf(temperature)+ String.valueOf((char) 0x00B0)+"C");
                        setFields(resp);

                        return v;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        return null;
                    }
                });*/

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
     if(!(isLocationSet)) {
          LatLng local = new LatLng(location.getLatitude(), location.getLongitude());
          Log.d("LOCATION",String.valueOf(local));
         // Creating a marker
         createMarker(local);
         isLocationSet=true;
     }
    }

    private void createMarker(LatLng local) {
        final MarkerOptions markerOptions = new MarkerOptions();
        // Setting the position for the marker
       // googleMap.clear();
        markerOptions.position(local);
        Marker loc= googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(local));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(local));

        getWeather(local,loc.getId());
    }

    private void getWeather(final LatLng local, final String marker_id) {
        Call<ExtraMain> call= RetrofitHelper.getWeatherInfo(local);
        call.enqueue(new Callback<ExtraMain>() {
            @Override
            public void onResponse(Call<ExtraMain> call, Response<ExtraMain> response) {
                Log.d("RESP","IN on location changed response");
                FinalWeatherData finalWeatherData=new FinalWeatherData();
                finalWeatherData.setExtraMain(response.body());
                getForecastWeather(local, marker_id,finalWeatherData);
             //   gotWeather=true;
            }

            @Override
            public void onFailure(Call<ExtraMain> call, Throwable t) {
                Log.d("RESP","IN onlocation changed failure"+t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MapsActivity.this, "Failed to update weather..", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getForecastWeather(LatLng latLng, final String marker_id, final FinalWeatherData finalWeatherData) {
        Call<WholeDay> call=RetrofitHelper.getWholedayWeather(latLng);
        call.enqueue(new Callback<WholeDay>() {
            @Override
            public void onResponse(Call<WholeDay> call, Response<WholeDay> response) {
                Log.d("FORECAST","In OnResp();");
                List<Forecast_Wholeday> tempList = new ArrayList<>();
                for(int i=0;i<(response.body().getCnt());i++)
                {
                    int date=response.body().getList().get(i).getDt();
                   String day= new SimpleDateFormat("dd/MM/yyyy").format(new Date(date*1000L));
                   String time=new SimpleDateFormat("HH:mm").format(new Date(date*1000L));
                   String description=response.body().getList().get(i).getWeather().get(0).getDescription();
                   double temp=response.body().getList().get(i).getMain().getTemp()-273.15;
                   Forecast_Wholeday wholeday_data=new Forecast_Wholeday(time,temp, description);
                    tempList.add(wholeday_data);

                   Log.d("Time= ",time);
                   Log.d("DATE= ",String.valueOf(date));
                   Log.d("day= ",day);
                   Log.d("Temp= ",String.valueOf(temp));
                }
                    finalWeatherData.setForecast_wholeday(tempList);
                    marker_map.put(marker_id,finalWeatherData);
                    setFields(finalWeatherData.getExtraMain());
                    adapter = new MyRecyclerViewAdapter(MapsActivity.this, tempList);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WholeDay> call, Throwable t) {
                Log.d("FORECAST","IN ONFail();");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MapsActivity.this, "Failed to update forecast weather..", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {


    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public boolean onMyLocationButtonClick() {
    //    Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
        return true;
    }


    public void setFields(ExtraMain response)
    {
        Log.d("OP","In setFields method");

        String description=String.valueOf(response.getWeather().get(0).getDescription());
        int temperature= (int) (response.getMain().getTemp()-273.15);
        String pressure=String.valueOf(response.getMain().getPressure());
        String sunrise=String.valueOf(new SimpleDateFormat("HH:mm a").format(response.getSys().getSunrise()*1000L));
        String sunset=String.valueOf(new SimpleDateFormat("HH:mm a").format(response.getSys().getSunset()*1000L));

        temp.setText(String.valueOf(temperature)+(char)0x00B0);
        press.setText("Pressure\n"+pressure);
        desc.setText(description.toUpperCase());
        sun_rise.setText("Sunrise\n"+sunrise);
        sun_set.setText("Sunset\n"+sunset);

        switch (description)
        {
            case "clear sky" : icon.setImageResource(R.drawable.clear_sky);
                break;
            case "few clouds" : icon.setImageResource(R.drawable.few_clouds);
                break;
            case "scattered clouds" : icon.setImageResource(R.drawable.scattered_clouds);
                break;
            case "broken clouds" : icon.setImageResource(R.drawable.broken_clousd);
                break;
            case "shower rain" : icon.setImageResource(R.drawable.rain);
                break;
            case "rain" : icon.setImageResource(R.drawable.shower_rain);
                break;
            case "thunderstorm" : icon.setImageResource(R.drawable.storm);
                break;
            case "snow" : icon.setImageResource(R.drawable.mor_snow);
                break;
            case "mist" : icon.setImageResource(R.drawable.mist);
                break;
            case "moderate rain":icon.setImageResource(R.drawable.rain);
            break;
            case "light rain":icon.setImageResource(R.drawable.rain);
                break;
            case "haze":icon.setImageResource(R.drawable.haze);
                break;
            default:icon.setImageResource(R.drawable.mist);

        }
    }


    @Override
    public void OnItemClick(int position) {
        Toast.makeText(this, "You clicked "+adapter.getItem(position), Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        FinalWeatherData finalWeatherData=marker_map.get(marker.getId());
        Log.d("Marker ID= ",marker.getId());
        ExtraMain extraMain = finalWeatherData.getExtraMain();
        wholedays=finalWeatherData.getForecast_wholeday();

        Log.d("WHOLEDAYS : ",wholedays.toString());

        adapter = new MyRecyclerViewAdapter(this, wholedays);
        recyclerView.setAdapter(adapter);
        setFields(extraMain);
        // adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
      //  Toast.makeText(this,"Item selected item is at pos "+pos,Toast.LENGTH_LONG).show();

        switch (pos)
        {
            case 0:googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 2:googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 3:googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,"No Item selected",Toast.LENGTH_LONG).show();

    }
}
