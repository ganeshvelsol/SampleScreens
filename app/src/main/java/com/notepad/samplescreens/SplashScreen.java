package com.notepad.samplescreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    TextView mtext;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mtext=findViewById(R.id.mtext);
        sp=getSharedPreferences("status",MODE_PRIVATE);
        spe=sp.edit();
        String data=sp.getString("s",null);
        if (data!=null)
        {
            startActivity(new Intent(this,RegisterActivity.class));
            finish();
        }
        else
        {

            final Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.flip);
            mtext.startAnimation(an);
            an.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {
                }
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    finish();
                    Intent i = new Intent(SplashScreen.this,RegisterActivity.class);
                    startActivity(i);
                    spe.putString("s","true");
                    spe.commit();
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
