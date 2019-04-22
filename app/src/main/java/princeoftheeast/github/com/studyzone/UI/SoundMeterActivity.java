package princeoftheeast.github.com.studyzone.UI;

/*
https://stackoverflow.com/questions/9597767/decibel-sound-meter-for-android
The above forum was used in constructing this class
 */
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import princeoftheeast.github.com.studyzone.R;

public class SoundMeterActivity extends Activity {

    private TextView soundLevelDB;
    private TextView soundLevelEnvironment;
    private ProgressBar progressBar;
    private MediaRecorder mediaRecorder;
    private Thread running;
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;

    final Runnable runnableUpdater = new Runnable(){
        public void run(){
            updateProgressBarAndTV();
        }
    };

    final Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sound_meter);
        soundLevelDB = (TextView) findViewById(R.id.tvSoundDB);
        soundLevelEnvironment = (TextView) findViewById(R.id.tvSoundLevelEnvironment);
        progressBar = (ProgressBar) findViewById(R.id.progessBar);


        if (running == null)
        {
            running = new Thread(){
                public void run()
                {
                    while (running != null)
                    {
                        try
                        {
                            Thread.sleep(1000);
                            Log.i("Sound", "Tick");
                        } catch (InterruptedException e) { };
                        handler.post(runnableUpdater);
                    }
                }
            };
            running.start();
            Log.d("Sound", "start runner()");
        }

    }

    public void onResume()
    {
        super.onResume();
        startRecorder();
    }

    public void onPause()
    {
        super.onPause();
        stopRecorder();
    }

    public void startRecorder(){
        if (mediaRecorder == null)
        {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null");
            try
            {
                mediaRecorder.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[Spider]", "IOException: " + android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Spider]", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }
            try
            {
                mediaRecorder.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Spider]", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }
        }

    }
    public void stopRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public void updateProgressBarAndTV(){
        int soundDB = (int)Math.round((soundDb(13)));

        //set text color
        if(soundDB<35) {
            soundLevelDB.setTextColor(Color.CYAN);
            soundLevelEnvironment.setTextColor(Color.CYAN);
        }
        else if(soundDB<45){
            soundLevelDB.setTextColor(Color.GREEN);
            soundLevelEnvironment.setTextColor(Color.GREEN);
        }
        else{
            soundLevelDB.setTextColor(Color.RED);
            soundLevelEnvironment.setTextColor(Color.RED);
        }

        //set sound environment
        if(soundDB<10) soundLevelEnvironment.setText("Normal Breathing");
        else if(soundDB<20) soundLevelEnvironment.setText("Whisper");
        else if(soundDB<30) soundLevelEnvironment.setText("Soft Whispering");
        else if(soundDB<45) soundLevelEnvironment.setText("Quiet Office/Library");
        else if(soundDB<65) soundLevelEnvironment.setText("Office/Conversion");
        else if(soundDB<75) soundLevelEnvironment.setText("Traffic");
        else if(soundDB<90) soundLevelEnvironment.setText("Heaevy Traffic");
        else soundLevelEnvironment.setText("Heavy Traffic");

        soundLevelDB.setText(soundDB + "dB(SPL)");
        progressBar.setProgress((int)Math.round(soundDb(13)), true);

    }
    public double soundDb(double ampitudel){
        return  20 * Math.log10(getAmpEMA() / ampitudel);
    }
    public double getAmp() {
        if (mediaRecorder != null) {
            return (mediaRecorder.getMaxAmplitude());
        }
        else {
            return 0;
        }
    }
    public double getAmpEMA() {
        double amp =  getAmp();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }

}
