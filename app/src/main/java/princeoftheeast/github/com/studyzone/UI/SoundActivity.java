package princeoftheeast.github.com.studyzone.UI;
/*
Sound files have been obtained from https://freesound.org/ and comply with the CC0 1.0 Universal (CC0 1.0)
Public Domain Dedication, Attribution 3.0 Unported (CC BY 3.0) and Attribution-NonCommercial 3.0 Unported (CC BY-NC 3.0) licenses.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import princeoftheeast.github.com.studyzone.Models.Sound;
import princeoftheeast.github.com.studyzone.Adapter.CustomSoundAdapter;
import princeoftheeast.github.com.studyzone.R;

public class SoundActivity extends AppCompatActivity {

    private ArrayList<Sound> soundArrayList;
    private CustomSoundAdapter soundAdapter;
    private ListView soundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        soundList = (ListView) findViewById(R.id.soundList);
        soundArrayList = new ArrayList<>();
        soundArrayList.add(new Sound("Summer Heavy Rainstorm with Thunder", "Legend1060", R.raw.summerheavyrainstormwiththunder));
        soundArrayList.add(new Sound("Hungary Meadow Afternoon Birds Crickets", "Alcappuccino", R.raw.hungarymeadowafternoonbirdscrickets));
        soundArrayList.add(new Sound("Beach Waves", "Suburbanwizard", R.raw.beachwavesclose));
        soundArrayList.add(new Sound("Autumn Wind and dry Leaves", "stek59", R.raw.autumnwindanddryleaves));
        soundArrayList.add(new Sound("Zen Ocean Waves,Ocean Waves Ambience", "innorecords", R.raw.zenoceanwavesoceanwavesambience));
        soundArrayList.add(new Sound("Sounds of Nature", "alanbenjamin0", R.raw.soundsofnature));

        soundAdapter = new CustomSoundAdapter(this, R.layout.activity_sound_single_item, soundArrayList);
        soundList.setAdapter(soundAdapter);

    }

}
