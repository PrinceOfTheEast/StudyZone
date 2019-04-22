package princeoftheeast.github.com.studyzone.UI;

/*https://www.youtube.com/watch?v=wW-AAXOy4u4&list=PLbte_tgDKVWRg-wgze_xTqC_Vzr7qxGZG&index=2
The above YouTube tutorial was consulted in creating this activity
***/
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import princeoftheeast.github.com.studyzone.Adapter.HomePageAdapter;
import princeoftheeast.github.com.studyzone.R;

import static princeoftheeast.github.com.studyzone.Adapter.CustomSoundAdapter.mediaPlayer;

public class HomePageActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        fbAuth = FirebaseAuth.getInstance();
        setupViewsUI();
        listViewSetup();
    }

    private void setupViewsUI(){
        listView = (ListView)findViewById(R.id.IvRows);
    }

    private void listViewSetup(){
        String[] titles = getResources().getStringArray(R.array.Main);
        String[] descriptions = getResources().getStringArray(R.array.Description);

        HomePageAdapter homePageAdapter = new HomePageAdapter(this, titles, descriptions);
        listView.setAdapter(homePageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: {
                        Intent intent = new Intent(HomePageActivity.this, WeeklyActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(HomePageActivity.this, SoundMeterActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        Intent intent = new Intent(HomePageActivity.this, SoundActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:{
                        Intent intent = new Intent(HomePageActivity.this, TimerActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            //when logoutMenu is clicked user is logged out and redirected to the MainActivity page
            case R.id.menuLogout: {
                Logout();
                break;
            }
            case R.id.menuProfile:{
                startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void Logout(){
        fbAuth.signOut();
        finish();
        if(mediaPlayer != null) {
            mediaPlayer.release();
        }

        startActivity(new Intent(HomePageActivity.this, MainActivity.class));
    }
}
