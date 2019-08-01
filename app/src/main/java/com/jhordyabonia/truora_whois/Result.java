package com.jhordyabonia.truora_whois;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhordyabonia.util.Server;
import com.jhordyabonia.webservice.Asynchtask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.jhordyabonia.truora_whois.MainActivity.API;

public class Result extends AppCompatActivity implements  View.OnClickListener {

    private SharedPreferences file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.back).setOnClickListener(this);

        Intent intent = getIntent();
        if(intent==null)
		{	finish();return;}
        String result=intent.getStringExtra(API);
        ((TextView)findViewById(R.id.result)).setText(result);
        try{  
          JSONObject json = new JSONObject(result);
          setImage(json.getString("Logo"));
        }catch(JSONException e){}
        
    }
  
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:         
                finish();
                break;
        }

    }
    private void setImage(final String image)
    {
        (new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected synchronized Bitmap doInBackground(String... fotos)
            {
                Bitmap imagen=null ;
                try
                {
                        URL imageUrl = new URL(image);
                        HttpURLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        imagen = BitmapFactory.decodeStream(inputStream);

                }catch (IOException e){}

                return imagen;
            }
            @Override
            protected void onPostExecute(Bitmap bitmap)
            {
                ((ImageView)Result.this.findViewById(R.id.logo))
                        .setImageBitmap(bitmap);
            }
        }).execute();
    }
}
