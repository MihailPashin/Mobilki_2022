package com.example.secondattempt_timer_paskhin_kislov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
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
    int Until_The_end;
    SwitchCompat toggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.countdown);
        toggleButton = findViewById(R.id.user_switch);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(mTimerRunning)
                    Start_Timer();
                }
                else
                {
                    if(!mTimerRunning)
                    pauseTimer();
                }
            }
        });

    }
    private void Start_Timer()
    {
        Min = (EditText) findViewById(R.id.user_minut);
        Sec = (EditText) findViewById(R.id.user_second);


        SoundRing = MediaPlayer.create(this, R.raw.smb);
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


        mCountDownTimer = new CountDownTimer(Mil, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int Mill = (int)millisUntilFinished;
                Until_The_end = Mill;
                int M2 = (Mill / 1000) / 60;
                int S2 = (Mill / 1000) % 60;
                updateCountDownText(M2, S2);
            }

            @Override
            public void onFinish() {
                SoundPlay (SoundRing);



            }
        }.start();
        mTimerRunning = true;
        toggleButton.setChecked(false);
        resetTimer();
        mTimerRunning = false;
        toggleButton.setChecked(true);

    }
    public void Res(View view){
        Min=(EditText)findViewById(R.id.user_minut);
        Sec=(EditText)findViewById(R.id.user_second);

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
        if(toggleButton.isChecked())
            Start_Timer();
        if(!toggleButton.isChecked())
            pauseTimer();
    }
    private void resetTimer() {
        pauseTimer();
        updateCountDownText(0, 0);
        if(!switchbtn)
        {
            Min=(EditText)findViewById(R.id.user_minut);
            Sec=(EditText)findViewById(R.id.user_second);

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

    }
    public void SoundPlay(MediaPlayer sound){
        sound.start();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();

    }
    private void updateCountDownText(int M, int S) {

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", M, S);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

}