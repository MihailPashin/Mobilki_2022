package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.CountDownTimer;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewCountDown;
    private EditText Min;
    private EditText Sec;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private boolean switchbtn; // включена или выключена кнопка
    private boolean OnePushBtn = true;
    private MediaPlayer SoundRing;
    int DoKontsaOst;


    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView img = findViewById(R.id.animationView);
                img.setBackgroundResource(R.drawable.schasi);
                AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
                if (isChecked) {
                    if (!mTimerRunning) {

                        startTimer();
                        frameAnimation.start();
                        switchbtn = true;
                    }
                } else {
                    if(mTimerRunning) {

                        pauseTimer();
                        frameAnimation.stop();
                        switchbtn = false;
                    }
                }
            }
        });
    }
    public void Res(View view){
        Min=(EditText)findViewById(R.id.etMin);
        Sec=(EditText)findViewById(R.id.etSec);

        int S, M;
        if(Sec.getText().toString().length()==0) {Sec.setText("0");}
        S =Integer.parseInt(Sec.getText().toString());
        if(S<0 || S>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(Min.getText().toString().length()==0) {Min.setText("0");}
        M =Integer.parseInt(Min.getText().toString());
        if(M<0 || M>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }


        updateCountDownText(M, S);
        resetTimer();
        OnePushBtn = true;
        if(switchbtn)
        startTimer();
        if(!switchbtn)
        pauseTimer();
    }
    private void startTimer() {

        Min = (EditText) findViewById(R.id.etMin);
        Sec = (EditText) findViewById(R.id.etSec);


        SoundRing = MediaPlayer.create(this, R.raw.bell);
        int S, M;
        if(Sec.getText().toString().length()==0) {Sec.setText("0");}
        S =Integer.parseInt(Sec.getText().toString());
        if(S<0 || S>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(Min.getText().toString().length()==0) {Min.setText("0");}
        M =Integer.parseInt(Min.getText().toString());
        if(M<0 || M>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

            int Mil = ((M * 60) + S) * 1000;
            if(!OnePushBtn)
            Mil = DoKontsaOst;

        mCountDownTimer = new CountDownTimer(Mil, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int Mill = (int)millisUntilFinished;
                DoKontsaOst = Mill;
                int M2 = (Mill / 1000) / 60;
                int S2 = (Mill / 1000) % 60;
                updateCountDownText(M2, S2);
            }

            @Override
            public void onFinish() {
                SoundPlay (SoundRing);
                mTimerRunning = false;
            }
        }.start();
        OnePushBtn = false;
        mTimerRunning = true;

    }
    public void SoundPlay(MediaPlayer sound){
        sound.start();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }
    private void resetTimer() {
        pauseTimer();
        updateCountDownText(0, 0);
        Min=(EditText)findViewById(R.id.etMin);
        Sec=(EditText)findViewById(R.id.etSec);

        int S, M;
        if(Sec.getText().toString().length()==0) {Sec.setText("0");}
        S =Integer.parseInt(Sec.getText().toString());
        if(S<0 || S>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(Min.getText().toString().length()==0) {Min.setText("0");}
        M =Integer.parseInt(Min.getText().toString());
        if(M<0 || M>59) {
            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
    }
    private void updateCountDownText(int M, int S) {

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", M, S);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}