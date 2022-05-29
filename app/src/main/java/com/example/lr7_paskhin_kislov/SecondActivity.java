package com.example.lr7_paskhin_kislov;
import android.bluetooth.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SecondActivity extends AppCompatActivity {
    private SeekBar mRedSeekBar, mGreenSeekBar, mBlueSeekBar, row, line;
    private Button BtColor, Back;
    private TextView Row, Line, ZnR, ZnG, ZnB, textAddress;
    private String Address;
    private String Result;
    BluetoothSocket clientSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Address = getIntent().getStringExtra("Address");

        BtColor = (Button)findViewById (R.id.sending);
        Back = (Button)findViewById (R.id.return_bt);
        mRedSeekBar = (SeekBar) findViewById(R.id.seekBarR);
        mGreenSeekBar = (SeekBar) findViewById(R.id.seekBarG);
        mBlueSeekBar = (SeekBar) findViewById(R.id.seekBarB);
        row = (SeekBar) findViewById(R.id.Row);
        line = (SeekBar) findViewById(R.id.Line);
        Row = (TextView) findViewById(R.id.textViewRow);
        Line = (TextView) findViewById(R.id.textViewLine);
        ZnR = (TextView) findViewById(R.id.textViewR);
        ZnG = (TextView) findViewById(R.id.textViewG);
        ZnB = (TextView) findViewById(R.id.textViewB);
        textAddress = (TextView) findViewById(R.id.textView5);
        Result = "$1 1 0 0 0;";

        updateBackground();
        mRedSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mGreenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mBlueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        row.setOnSeekBarChangeListener(seekBarChangeListener);
        line.setOnSeekBarChangeListener(seekBarChangeListener);
        textAddress.setText("Подключено к " + Address);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        BtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                startActivityForResult(new Intent(enableBT), 0);
                BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                try{
                    //Устройство с данным адресом - наш Bluetooth Bee
                    //Адрес опредеяется следующим образом: установите соединение
                    //между ПК и модулем (пин: 1234), а затем посмотрите в настройках
                    //соединения адрес модуля. Скорее всего он будет аналогичным.
                    BluetoothDevice device = bluetooth.getRemoteDevice(Address);

                    //Инициируем соединение с устройством
                    Method m = device.getClass().getMethod(
                            "createRfcommSocket", new Class[] {int.class});

                    clientSocket = (BluetoothSocket) m.invoke(device, 1);
                    clientSocket.connect();

                    //В случае появления любых ошибок, выводим в лог сообщение
                } catch (IOException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                } catch (SecurityException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                } catch (NoSuchMethodException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                } catch (IllegalArgumentException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                } catch (IllegalAccessException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                } catch (InvocationTargetException e) {
                    Log.d("BLUETOOTH", e.getMessage());
                }

                try {
                    //Получаем выходной поток для передачи данных
                    OutputStream outStream = clientSocket.getOutputStream();

                    //Пишем данные в выходной поток
                    outStream.write(Result.getBytes());

                } catch (IOException e) {
                    //Если есть ошибки, выводим их в лог
                    Log.d("BLUETOOTH", e.getMessage());
                }
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateBackground();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void updateBackground() {
        int redValue, greenValue, blueValue;
        int rowValue, lineValue;
        redValue = mRedSeekBar.getProgress();
        greenValue = mGreenSeekBar.getProgress();
        blueValue = mBlueSeekBar.getProgress();
        rowValue = row.getProgress();
        lineValue = line.getProgress();
        // меняем фон через формат RGB
        ZnR.setText("Красный: " + redValue);
        ZnG.setText("Зеленый: " + greenValue);
        ZnB.setText("Синий: " + blueValue);
        Row.setText("Строка:" + rowValue);
        Line.setText("Ряд :" + lineValue);
        Result = String.format("$%d %d %d %d %d;", rowValue, lineValue, redValue, greenValue, blueValue);
        if ((redValue == 0)&&(greenValue==0)&&(blueValue==0))
        {
            BtColor.setTextColor(getResources().getColor(R.color.white));
        }
        if ((redValue == 255)&&(greenValue==255)&&(blueValue==255))
        {
            BtColor.setTextColor(getResources().getColor(R.color.black));
        }
        BtColor.setBackgroundColor(0xff000000 + redValue * 0x10000 + greenValue * 0x100
                + blueValue);
    }
}

