package com.example.user3.weatherinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user3 on 8/5/18.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mday;
    private List<String> mtemp;
    List<Forecast_Wholeday> wholedayList=new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClicked onClick;
    private LatLng point;
    public interface OnItemClicked
    {
        void OnItemClick(int position);

    }

    public MyRecyclerViewAdapter(Context context,List<Forecast_Wholeday> whole_day_weather) {
        this.mInflater=LayoutInflater.from(context);
        wholedayList=whole_day_weather;
        this.mContext=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int vtemp= (int) wholedayList.get(position).getTemperature();
        String vtime=wholedayList.get(position).getTime();
        String vdesc=wholedayList.get(position).getDescription();
        Log.d("VDESC= ",vdesc);

        holder.time.setText(vtime);
        holder.temp.setText(String.valueOf(vtemp)+(char)0x00B0);


        switch (vdesc)
        {
            case "clear sky" : holder.icon.setImageResource(R.drawable.clear_sky);
                break;
            case "few clouds" : holder.icon.setImageResource(R.drawable.few_clouds);
                break;
            case "scattered clouds" : holder.icon.setImageResource(R.drawable.scattered_clouds);
                break;
            case "broken clouds" : holder.icon.setImageResource(R.drawable.broken_clousd);
                break;
            case "shower rain" : holder.icon.setImageResource(R.drawable.rain);
                break;
            case "rain" : holder.icon.setImageResource(R.drawable.shower_rain);
                break;
            case "thunderstorm" : holder.icon.setImageResource(R.drawable.storm);
                break;
            case "snow" : holder.icon.setImageResource(R.drawable.mor_snow);
                break;
            case "mist" : holder.icon.setImageResource(R.drawable.mist);
                break;
            case "moderate rain":holder.icon.setImageResource(R.drawable.rain);
                break;
            case "light rain":holder.icon.setImageResource(R.drawable.rain);
                break;
            case "haze":holder.icon.setImageResource(R.drawable.haze);
                break;
            default:holder.icon.setImageResource(R.drawable.mist);
        }


    }

    @Override
    public int getItemCount() {
        return wholedayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView time,temp;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            time=(TextView) itemView.findViewById(R.id.tv_day);
            temp=(TextView) itemView.findViewById(R.id.tv_temp);
            icon=(ImageView)itemView.findViewById(R.id.iv_icon);




        }
        public String getItem(int id)
        {
            return mday.get(id);

        }


    }

    public String getItem(int id)
    {
        return mday.get(id);
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
    }

