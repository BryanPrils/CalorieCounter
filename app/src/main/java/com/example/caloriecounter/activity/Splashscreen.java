package com.example.caloriecounter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.caloriecounter.R;

public class Splashscreen extends Activity {

    Thread splashThread;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = (ImageView) findViewById(R.id.splash);

        splashThread = new Thread(){
            @Override
            public void run() {
                try{
                    int waited = 0;
                    while (waited < 1500){
                        sleep(100);
                        waited +=100;
                    }
                    Intent intent;
                    intent = new Intent(Splashscreen.this,
                            AccountSignUpActivity.class);
                    startActivity(intent);
                }catch (InterruptedException e){

                }finally {
                    Splashscreen.this.finish();
                }
            }
        };
        splashThread.start();
    }
}
