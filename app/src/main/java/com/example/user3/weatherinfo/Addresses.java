package com.example.user3.weatherinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Addresses extends AppCompatActivity  {
    TextView saddr,start,end,eaddr;
    GoogleMap googleMap;
    double lat,lng,elat,elng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        saddr=(TextView)findViewById(R.id.tv_saddr);
        start=(TextView)findViewById(R.id.tv_start);
        eaddr=(TextView)findViewById(R.id.tv_eaddr);
        end=(TextView)findViewById(R.id.tv_end);
        final Bundle extra=getIntent().getExtras();
        String start_addr=extra.getString("start_addr");
        start.setText(start_addr);
        lat=Double.parseDouble(extra.getString("lat"));
        lng=Double.parseDouble(extra.getString("lng"));
        elat=extra.getDouble("elat");
        elng=extra.getDouble("elng");
        end.setText(extra.getString("end_addr"));

        saddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Addresses.this,StartAddress.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        eaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Addresses.this,StartAddress.class);
                intent.putExtra("lat",elat);
                intent.putExtra("lng",elng);
                startActivity(intent);

            }
        });

    }


}
