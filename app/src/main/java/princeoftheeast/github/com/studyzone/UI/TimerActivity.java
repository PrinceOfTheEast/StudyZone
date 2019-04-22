package princeoftheeast.github.com.studyzone.UI;
/*
The sound file used in this class was obtained from https://freesound.org/people/Alxy/sounds/189327/ and complies with all the licenses stated on the site
 */
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import princeoftheeast.github.com.studyzone.R;

public class TimerActivity extends AppCompatActivity {
    private EditText inputTime;
    private TextView timerCountdown;
    private TextView timerTitle;
    private Button setTime;
    private Button startPause;
    private Button reset;

    private CountDownTimer countDownTimer;

    private boolean isTimerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis = startTimeInMillis;
    private long endTime;

    private MediaPlayer mplayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        inputTime = (EditText)findViewById(R.id.etInputTime);
        setTime = (Button)findViewById(R.id.btnSetTime);
        timerCountdown = (TextView) findViewById(R.id.tvTimerCountdown);
        startPause = (Button)findViewById(R.id.btnStartPause);
        reset = (Button) findViewById(R.id.btnReset);

        timerTitle =(TextView)findViewById(R.id.tvTimerTitle);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputT = inputTime.getText().toString();
                if(inputT.length() == 0){
                    Toast.makeText(TimerActivity.this, "Please enter a time", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInputed = Long.parseLong(inputT)*60000;
                if(millisInputed==0){
                    Toast.makeText(TimerActivity.this, "Time cannot be negative", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTheTime(millisInputed);
                inputTime.setText("");
            }
        });

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning){
                    timerPause();
                }else{
                    timerStart();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerReset();
            }
        });

        updateTimerCountdown();
    }

    private void setTheTime(long milliSeconds){
        startTimeInMillis = milliSeconds;
        timerReset();
        hideKeyboard();
    }

    private void timerStart(){
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisLeft) {
                timeLeftInMillis = millisLeft;
                updateTimerCountdown();
            }

            @Override
            public void onFinish() {
                play();
                isTimerRunning = false;
                updateTimerInterface();
            }
        }.start();

        isTimerRunning = true;
        updateTimerInterface();
    }


    private void timerPause(){
        countDownTimer.cancel();
        isTimerRunning = false;
        updateTimerInterface();
    }

    private void timerReset(){
        timeLeftInMillis = startTimeInMillis;
        updateTimerCountdown();
        updateTimerInterface();
    }

    private void updateTimerCountdown(){
        int hours = (int) (timeLeftInMillis/1000)/3600;
        int min = (int) ((timeLeftInMillis/1000)%3600)/60;
        int sec = (int) (timeLeftInMillis/1000)%60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, min, sec);
        }else{
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, sec);
        }

        timerCountdown.setText(timeLeftFormatted);
    }

    private void updateTimerInterface(){
        if(isTimerRunning){
            inputTime.setVisibility(View.INVISIBLE);
            setTime.setVisibility(View.INVISIBLE);
            reset.setVisibility(View.INVISIBLE);
            startPause.setText("Pause");
        }else{
            inputTime.setVisibility(View.VISIBLE);
            setTime.setVisibility(View.VISIBLE);
            startPause.setText("Start");

            if(timeLeftInMillis < 1000 && isTimerRunning){
                startPause.setVisibility(View.INVISIBLE);
            }else{
                startPause.setVisibility(View.VISIBLE);
            }
            if(timeLeftInMillis < startTimeInMillis){
                reset.setVisibility(View.VISIBLE);
            }else{
                reset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void play(){
        if(mplayer == null) {
            mplayer = MediaPlayer.create(this, R.raw.missilelockonsound);//sound file does not infringe any copyright law
            mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        mplayer.start();
    }

    private void stopPlayer(){
        if(mplayer !=null){
            mplayer.release();
            mplayer = null;
            Toast.makeText(this, "Media player released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisleft", timeLeftInMillis);
        outState.putBoolean("timerRunning", isTimerRunning);
        outState.putLong("endTime", endTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        timeLeftInMillis = savedInstanceState.getLong("millisLeft");
        isTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateTimerCountdown();
        updateTimerInterface();

        if(isTimerRunning){
            endTime = savedInstanceState.getLong("endTime");
            timeLeftInMillis = endTime - System.currentTimeMillis();
            timerStart();
        }
    }

    //back button will turn of mediaPlayeer as timer is reset to 0 so timer should not go off.
    @Override
    public void onBackPressed() {

        if(countDownTimer!=null){
            countDownTimer.cancel();
            Toast.makeText(this,"Timer cancelled", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this,"No timer set", Toast.LENGTH_LONG).show();

        super.onBackPressed();
    }

}
