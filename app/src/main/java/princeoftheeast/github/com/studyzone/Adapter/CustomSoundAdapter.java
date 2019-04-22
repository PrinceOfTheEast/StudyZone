package princeoftheeast.github.com.studyzone.Adapter;
/*
https://www.youtube.com/watch?v=tZM4EF88OFk
The above youtube tutorial was referred to when creating this class
 */
import android.content.Context;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import princeoftheeast.github.com.studyzone.Models.Sound;
import princeoftheeast.github.com.studyzone.R;


public class CustomSoundAdapter extends BaseAdapter {

    private Context aContext;
    private int alayout;
    private ArrayList<Sound> soundArrayList;
    public static MediaPlayer mediaPlayer;

    public CustomSoundAdapter(Context aContext, int alayout, ArrayList<Sound> soundArrayList) {
        this.aContext = aContext;
        this.alayout = alayout;
        this.soundArrayList = soundArrayList;
    }

    @Override
    public int getCount() {
        return soundArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView soundName, soundArtist;
        ImageView play, pause, stop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(alayout, null);
            viewHolder.soundName = (TextView) convertView.findViewById(R.id.tvSoundName);
            viewHolder.soundArtist = (TextView)convertView.findViewById(R.id.tvArtist);
            viewHolder.play = (ImageView) convertView.findViewById(R.id.ivPlaySound);
            viewHolder.pause = (ImageView) convertView.findViewById(R.id.ivPauseSound);
            viewHolder.stop = (ImageView) convertView.findViewById(R.id.ivStopSound);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Sound sound = soundArrayList.get(position);

        viewHolder.soundName.setText(sound.getName());
        viewHolder.soundArtist.setText(sound.getArtist());

        //play sound
        viewHolder.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(aContext, sound.getSound());
                }
                mediaPlayer.start();
            }
        });
        //pause sound
        viewHolder.pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                }
            }
        });
        //stop sound
        viewHolder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

            }
        });

        return convertView;
    }
}
