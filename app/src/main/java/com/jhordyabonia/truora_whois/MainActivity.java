package com.jhordyabonia.truora_whois;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jhordyabonia.util.Server;
import com.jhordyabonia.webservice.Asynchtask;

public class MainActivity extends AppCompatActivity  implements Asynchtask, View.OnClickListener {

    public static final String API = "api";
    private SharedPreferences file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.find).setOnClickListener(this);
        findViewById(R.id.history).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        file =  getSharedPreferences(API, Context.MODE_PRIVATE);
        Server.URL_SERVER = file.getString(API,Server.URL_SERVER);
        if(Server.URL_SERVER.isEmpty())
            startActivity(new Intent(this,Setting.class));
    }
    
    @Override
    public void processFinish(String result) {
        Intent intent = new Intent(this,Result.class);
        intent.putExtra(API, result);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find:
                String url= ((EditText)findViewById(R.id.q))
                        .getText().toString();                
                Server.send("/api/analyce/"+url, this, this);

                break;
            case R.id.history:                
                Server.send("/api/list", this, this);
                break;
            case R.id.setting: 
                startActivity(new Intent(this,Setting.class));
                break;
        }

    }
}
