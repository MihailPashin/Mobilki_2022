package com.example.time_paskhin_kislov;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.CountDownTimer;
import android.widget.*;
import android.os.Handler;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private EditText user_minut;
    private EditText user_second;
    //private boolean btn_status;
    int value_for_pause;
    boolean Pause_Appearing=false;
    TextView CountDown_Text;
    private CountDownTimer mCountDownTimer;
    private boolean Timer_Zapuzhen;
    ToggleButton tg_btn;
    private MediaPlayer Super_Mario;
    private static final String TAG ="esa";
    private static final String TAG_2="animation";
    private static final String START_TAG="Начальное_Время";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         CountDown_Text=findViewById(R.id.countdown_text);
         tg_btn= (ToggleButton) findViewById(R.id.user_switch);
         ImageView img= findViewById(R.id.animationView);
         img.setBackgroundResource(R.drawable._some_animation);

        AnimationDrawable frm_anim= (AnimationDrawable) img.getBackground();
        tg_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    if(!Timer_Zapuzhen)
                    {
                        //frm_anim.start();

                        if(Checking_Minutes_Second()!=null)
                        {  runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                frm_anim.start();
                                Log.e(TAG_2, "старт");
                            }
                        });
                            Timer_Zapuzhen=true;
                            Timer_Starting();
                            }
                        else { mHandler.sendEmptyMessage(3);}
                        //New_Timer_Starting();
                        }
                } else {
                    if(frm_anim.isRunning())
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                frm_anim.stop();
                                Log.e(TAG_2, "остановка");
                            }
                        });
                        if(Timer_Zapuzhen)
                        {
                            pauseTimer();

                            Timer_Zapuzhen = false;
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    frm_anim.selectDrawable(0);
                                    Log.e(TAG_2, "остановка");
                                }
                            });
                        }
                    }


                }
            }
        });
    }
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast toast_finished = Toast.makeText(MainActivity.this, "IT'S FINE", Toast.LENGTH_LONG);
                    toast_finished.show();
                    break;
                case 2:
                    Toast toast_reset = Toast.makeText(MainActivity.this, "Сброс", Toast.LENGTH_LONG);
                    toast_reset.show();
                    break;
                case 3:
                    Toast toast_unexpected = Toast.makeText(MainActivity.this, "Что-то пошло не так", Toast.LENGTH_LONG);
                    toast_unexpected.show();
                    break;
            }
        }
    };
    private void New_Timer_Starting() {
        try {
            int[] new_minutes_second = Checking_Minutes_Second();
            int Mil;

            Super_Mario = MediaPlayer.create(this, R.raw.super_mario);

            TimeSpan ts = new TimeSpan(new_minutes_second[1], new_minutes_second[0]);

            Countdown(ts);
        } catch (Exception e) {

            Toast toast = Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private  void Countdown(TimeSpan ts) // метод обратного отсчёта
    {
        //g = true; // для отображения на label обратного отсчёта
        //button1.BackColor = DefaultBackColor;// скрытие кнопки сброс отсчёта
       // boolean until_ending=true;
        ImageView img= findViewById(R.id.animationView);
        AnimationDrawable frm_anim= (AnimationDrawable) img.getBackground();


        Runnable r = ()->{

            try{
                synchronized(this) {
                    // TimeSpan ts = new TimeSpan(minutes, seconds); // создаём экземпляр класса TimeSpan. Важное его преимущество над Timer-Принятие интовых переменных

                    while (ts.Until_ending()) // До тех пора не истёк отсчёт. reset =true если сбросили таймер
                    {
                        //runOnUiThread(new Runnable() {
                        //  @Override
                        // public void run() {
                        //    frm_anim.start();
                        //}
                        //});
                        Thread.sleep(1000); // ожидание секунды
                        ts.FromSeconds();

                        CountDown_Text.post(new Runnable() {
                            @Override
                            public void run() {
                                Set_text(ts);
                            }
                        });
                        mHandler.sendEmptyMessage(1);

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            frm_anim.stop();
                        }
                    });

                    mHandler.sendEmptyMessage(1);

                }
               

            }
            catch(Exception e){
                Toast toast = Toast.makeText(MainActivity.this, "Something_Wrong", Toast.LENGTH_LONG);
                toast.show();

            }

        };

            CountDown_Text.setText(ts.ToString());
            Thread myThread = new Thread(r,"MyThread");

            myThread.start();
            Pause_Appearing = true;
            Timer_Zapuzhen = true;




    }

    private void Set_text(TimeSpan ts)
    { TextView text_second= findViewById(R.id.countdown_text);
        text_second.setText(ts.ToString());
    }
        private void Timer_Starting() {
        int[] minutes_second = Checking_Minutes_Second();
        int Mil;
        TextView text_second= findViewById(R.id.textView4);
        Super_Mario = MediaPlayer.create(this, R.raw.super_mario);
        //Log.e(START_TAG, String.format(String.format("%d:%d", minutes_second[0], minutes_second[1])));
        if(minutes_second !=null)
        {
            if (!Pause_Appearing) {
                Mil = ((minutes_second[0] * 60) + minutes_second[1]) * 1000;
            } else {
                Mil = value_for_pause;
                Pause_Appearing = false;
            }

            mCountDownTimer = new CountDownTimer(Mil, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int Mill = (int) millisUntilFinished;

                    value_for_pause = Mill;
                    Log.e(String.format("%d",Mill), "итерация");
                    int M2 = (Mill/1000) / 60  ;
                    int S2 = ((Mill/1000) % 60);
                    updateCountDownText(M2,S2 );


                }

                @Override
                public void onFinish() {
                    Log.e(TAG, "конец");
                    mHandler.sendEmptyMessage(1);
                    Pause_Appearing = false;
                    Timer_Zapuzhen = false;
                    SoundPlay(Super_Mario);
                    tg_btn.post(new Runnable() {
                        @Override
                        public void run() {
                            tg_btn.setChecked(false);
                        }
                    });
                }
            }.start();
            Pause_Appearing = true;
            Timer_Zapuzhen = true;

        }

    }
    public void SoundPlay(MediaPlayer sound){
        sound.start();
    }

    private void resetTimer() {

        pauseTimer();

        mHandler.sendEmptyMessage(2);
        //nt[] values_of_time=Checking_Minutes_Second();
    }

    private int[] Checking_Minutes_Second() {

        int[] values_of_time = new int[2];
        user_minut = (EditText) findViewById(R.id.user_minut);
        user_second = (EditText) findViewById(R.id.user_second);
        int seconds, minutes;
        if (user_second.getText().toString().length() == 0) {
            user_second.setText("0");
        }
        values_of_time[1] = Integer.parseInt(user_second.getText().toString());
        if (values_of_time[1] < 0 || values_of_time[1] > 59) {
            return null;
        }
        if (user_minut.getText().toString().length() == 0) {
            user_minut.setText("0");
        }
        values_of_time[0] = Integer.parseInt(user_minut.getText().toString());
        if (values_of_time[0] < 0 || values_of_time[0] > 59) {
            return null;
        }
        if (values_of_time[1] == 0 & values_of_time[0] == 0) {
            return null;
        }
        return values_of_time;
    }

    private void updateCountDownText(int M, int S) {

        CountDown_Text.post(new Runnable() {
            @Override
            public void run() {
                CountDown_Text.setText(String.format("%d:%d", M, S));
            }
        });
    }
    private void pauseTimer() {

        mCountDownTimer.cancel();

    }

    public void For_reset(View view) {

        int[] values_of_time=Checking_Minutes_Second();
        if(values_of_time!=null)
        {updateCountDownText(values_of_time[0], values_of_time[1]);
            resetTimer();
            Pause_Appearing = false;
            if(Timer_Zapuzhen)
                Timer_Starting();
            if(!Timer_Zapuzhen)
                pauseTimer();
        }
        else{mHandler.sendEmptyMessage(3);}


    }

}
