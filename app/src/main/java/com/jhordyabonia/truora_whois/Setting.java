package com.jhordyabonia.truora_whois;

import android.app.Activity;
import android.content.Context;
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

import static com.jhordyabonia.truora_whois.MainActivity.API;

public class Setting extends AppCompatActivity implements  View.OnClickListener {

    private SharedPreferences file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

        file =  getSharedPreferences(API, Context.MODE_PRIVATE);
        Server.URL_SERVER = file.getString(API,Server.URL_SERVER);
        ((EditText)findViewById(R.id.q)).setText(Server.URL_SERVER);
    }
  
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:              
                Server.URL_SERVER = ((EditText)findViewById(R.id.q))
                                .getText().toString();                 
                file =  getSharedPreferences(API, Context.MODE_PRIVATE);

			    SharedPreferences.Editor editor = file.edit();
                editor.putString(API,Server.URL_SERVER);
		        editor.commit();
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }

    }
}
