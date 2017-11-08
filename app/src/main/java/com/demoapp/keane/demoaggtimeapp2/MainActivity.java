package com.demoapp.keane.demoaggtimeapp2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar mySeekbar;
    private Button myButton;
    private TextView myTextVies; //= (TextView)findViewById(R.id.myTimeTextView);
    private StringBuilder timerStr = new StringBuilder(10);
    private boolean counterIsActive = false;
    private CountDownTimer countDownTimer;

    //改變顯示的分鐘數: 秒數
    public void updateTimer(int secondLeft) {
        int minutes = secondLeft / 60;
        int seconds = secondLeft - minutes * 60;
        timerStr.append(minutes).append(":");
        String ss = Integer.toString(seconds);

        //避免發生00:9  6:7  ==>00:09 6:07
        if (seconds <= 9) {
//            ss = "0" + ss;
            timerStr.append("0").append(seconds);
        }else {
            timerStr.append(seconds);
        }
//        myTextVies.setText(Integer.toString(minutes) + ":" + ss);
        myTextVies.setText(timerStr.toString());  //改變顯示文字
        timerStr.setLength(0);    //重置StringBuilder
    }

    //重置計時器
    public void reSetTimer() {
        myTextVies.setText("0:30");//顯示歸位0:30
        mySeekbar.setProgress(30);
        countDownTimer.cancel();
        mySeekbar.setEnabled(true); //啟用seekbar
        myButton.setText("GO");     //更改按鍵顯示文字
        counterIsActive = false;    //計時關閉
    }

    //計時器開啟後的動作
    public void controlTimer(View view) {
        if (counterIsActive == false) { //計時已啟動了嗎？false就表示沒有，所以要啟動
            counterIsActive = true;
            mySeekbar.setEnabled(false);
            myButton.setText("Stop");

            countDownTimer = new CountDownTimer(mySeekbar.getProgress() * 1000 + 100, 1000) {

                @Override//倒數每一秒
                public void onTick(long millisUntilFinished) {
                    //改變顯示文字
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override//倒數結束
                public void onFinish() {
                    reSetTimer();
                    //播放音樂
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.testsound);
                    mplayer.start();
//                    Log.i("finished", "timer done");
                }
            }.start();
        } else {
            reSetTimer();//重置計時器
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView(R.Layout.activity_main) 組畫面之前，去使用findViewById 會報錯
        //
        setContentView(R.layout.activity_main);
        myTextVies = (TextView) findViewById(R.id.myTimeTextView);
        mySeekbar = (SeekBar) findViewById(R.id.myTimeSeekBar);
        myButton = (Button) findViewById(R.id.myTimeButton);
        mySeekbar.setMax(600);          //設定seekbar的範圍
        mySeekbar.setProgress(30);      //設定seekbar的初始位置
//        //seekbar監聽器( new SeekBar.OnSeekBarChangeListener(){
//        //override 3個方法
//        // } );
//

        //移動拉把
        mySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override//拉把移動的數值
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("abc", "progess = " + progress);
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
//    在setContentView(R.Layout.activity_main) 組畫面之前，去使用findViewById 會報錯
//    /com.google.android.googlequicksearchbox:search W/ErrorReporter: reportError [type: 211, code: 524300]: Error reading from input stream
//11-02 14:59:40.014 2224-2542/com.google.android.googlequicksearchbox:search I/MicroRecognitionRunner: Stopping hotword detection.
//11-02 14:59:40.015 2224-3270/com.google.android.googlequicksearchbox:search W/ErrorProcessor: onFatalError, processing error from engine(4)
//    com.google.android.apps.gsa.shared.speech.a.g: Error reading from input stream
//    at com.google.android.apps.gsa.staticplugins.recognizer.i.a.a(SourceFile:342)
//    at com.google.android.apps.gsa.staticplugins.recognizer.i.a$1.run(SourceFile:1367)
//    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:428)
//    at java.util.concurrent.FutureTask.run(FutureTask.java:237)
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ak.run(SourceFile:66)
//    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1133)
//    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
//    at java.lang.Thread.run(Thread.java:761)
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ad$1.run(SourceFile:85)
//    Caused by: com.google.android.apps.gsa.shared.exception.GsaIOException: Error code: 393238 | Buffer overflow, no available space.
//    at com.google.android.apps.gsa.speech.audio.Tee.g(SourceFile:2531)
//    at com.google.android.apps.gsa.speech.audio.ap.read(SourceFile:555)
//    at java.io.InputStream.read(InputStream.java:101)
//    at com.google.android.apps.gsa.speech.audio.al.run(SourceFile:362)
//    at com.google.android.apps.gsa.speech.audio.ak$1.run(SourceFile:471)
//    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:428)
//    at java.util.concurrent.FutureTask.run(FutureTask.java:237)
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ak.run(SourceFile:66)
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ax.run(SourceFile:139)
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ax.run(SourceFile:139)
//    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1133) 
//    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607) 
//    at java.lang.Thread.run(Thread.java:761) 
//    at com.google.android.apps.gsa.shared.util.concurrent.a.ad$1.run(SourceFile:85) 
//            11-02 14:59:40.015 2224-3270/com.google.android.googlequicksearchbox:search I/AudioController: internalShutdown
//11-02 14:59:40.016 2224-2224/com.google.android.googlequicksearchbox:search I/MicroDetector: Keeping mic open: false
//            11-02 14:59:40.016 2224-2224/com.google.android.googlequicksearchbox:search I/MicroDetectionWorker: #onError(false)
//11-02 14:59:40.017 2224-3269/com.google.android.googlequicksearchbox:search I/DeviceStateChecker: DeviceStateChecker cancelled
//11-02 14:59:45.023 2224-2224/com.google.android.googlequicksearchbox:search I/MicroDetectionWorker: Micro detection mode: [mDetectionMode: [1]].
//            11-02 14:59:45.024 2224-2224/com.google.android.googlequicksearchbox:search I/AudioController: Using mInputStreamFactoryBuilder
//11-02 14:59:45.029 2224-3277/com.google.android.googlequicksearchbox:search I/MicroRecognitionRunner: Starting detection.
//            11-02 14:59:45.029 2224-2355/com.google.android.googlequicksearchbox:search I/MicrophoneInputStream: mic_starting com.google.android.apps.gsa.staticplugins.z.c@96e1505
//            11-02 14:59:45.031 1340-1669/? W/APM_AudioPolicyManager: getInputForAttr() failed opening input: samplingRate 16000, format 1, channelMask 10
//            11-02 14:59:45.031 2224-2355/com.google.android.googlequicksearchbox:search E/AudioRecord: Could not get audio input for session 457, record source 1999, sample rate 16000, format 0x1, channel mask 0x10, flags 0
//            11-02 14:59:45.031 2224-2355/com.google.android.googlequicksearchbox:search E/AudioRecord-JNI: Error creating AudioRecord instance: initialization check failed with status -22.
//            11-02 14:59:45.031 2224-2355/com.google.android.googlequicksearchbox:search E/android.media.AudioRecord: Error code -20 when initializing native AudioRecord object.
//            11-02 14:59:45.031 2224-2355/com.google.android.googlequicksearchbox:search I/MicrophoneInputStr
}
