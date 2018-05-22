package com.example.user3.weatherinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Instruction extends AppCompatActivity {
    TextView inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        inst=(TextView)findViewById(R.id.tv_inst);
        Bundle extra=getIntent().getExtras();
        String direction=extra.getString("directions");

        inst.setText(Html.fromHtml(direction));
    }
}
