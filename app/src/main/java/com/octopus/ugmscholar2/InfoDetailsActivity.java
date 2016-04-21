package com.octopus.ugmscholar2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoDetailsActivity extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        Intent intent = new Intent();
        intent = getIntent();
        title= (TextView) findViewById(R.id.title);
        title.setText(intent.getStringExtra("title"));


    }
}
