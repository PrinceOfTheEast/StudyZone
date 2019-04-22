package princeoftheeast.github.com.studyzone.UI;

/*https://www.youtube.com/watch?v=mw3t-trrnmU&t=587s
The above YouTube tutorial was consulted in creating this activity
***/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import princeoftheeast.github.com.studyzone.Adapter.WeeklyAdapter;
import princeoftheeast.github.com.studyzone.R;

public class WeeklyActivity extends AppCompatActivity {

    private ListView ourListView;
    public static String SELECTED_DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        setupViewsUI();
        listViewSetup();

    }

    private void setupViewsUI(){
        ourListView = (ListView)findViewById(R.id.lvWeekRows);
    }

    private void listViewSetup(){
        String[] weekly = getResources().getStringArray(R.array.wkly);

        WeeklyAdapter weeklyAdapter = new WeeklyAdapter(this, R.layout.activity_weekly_single_item, weekly);
        ourListView.setAdapter(weeklyAdapter);

        ourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: {
                        SELECTED_DAY = "Monday";//SELECTED_DAY is a String variable
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 1: {
                        SELECTED_DAY = "Tuesday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 2: {
                        SELECTED_DAY = "Wednesday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 3: {
                        SELECTED_DAY = "Thursday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 4: {
                        SELECTED_DAY = "Friday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 5: {
                        SELECTED_DAY = "Saturday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    case 6: {
                        SELECTED_DAY = "Sunday";
                        startActivity(new Intent(WeeklyActivity.this, DayActivity.class));
                        break;
                    }
                    default: break;
                }
            }
        });
    }
}
