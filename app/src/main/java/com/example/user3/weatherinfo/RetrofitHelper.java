package com.example.user3.weatherinfo;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user3 on 26/4/18.
 */

public class RetrofitHelper {






    public static Call<ExtraMain>  getWeatherInfo(LatLng latLng)
    {

        String appid="4e2abb4d549c6a040f65a6f071566784";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HelperApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final HelperApi helperApi =retrofit.create(HelperApi.class);

        Call<ExtraMain> call= helperApi.getWeatherInfo(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),appid);

        return  call;

    }

    public static Call<DirectionInfo> getDirection(String origin,String destination)
    {

        Log.d("CALL ORIGIN",String.valueOf(origin));
        Log.d("CALL DESTINATION",String.valueOf(destination));

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

       String  key="AIzaSyDtcTUfBUDU20Jtc5m2vAjSbeLH29p_o7E";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HelperApi.BASIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        final HelperApi helperApi =retrofit.create(HelperApi.class);

        Call<DirectionInfo> call=helperApi.getDirections(origin,destination,key);

        return  call;



    }

    public static Call<WholeDay> getWholedayWeather(LatLng latLng)
    {

        String appid="557304b4f855cda9e148f9342c382483";
        String cnt="8";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HelperApi.BASIC_WEEKLY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final HelperApi helperApi =retrofit.create(HelperApi.class);

        Call<WholeDay> call= helperApi.getWholedayWeather(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),cnt,appid);

        return  call;

    }
}
