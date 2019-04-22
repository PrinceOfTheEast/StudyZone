package princeoftheeast.github.com.studyzone.Adapter;
/*https://www.youtube.com/watch?v=wW-AAXOy4u4&list=PLbte_tgDKVWRg-wgze_xTqC_Vzr7qxGZG&index=2
The above YouTube tutorial was consulted in creating this adapter
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

import princeoftheeast.github.com.studyzone.R;

public class HomePageAdapter extends BaseAdapter{

    private Context acontext;
    private LayoutInflater layoutInflater;
    private TextView theTitle, theDescription;
    private String[] arrayOfTitle;
    private String[] arrayOfDescriptions;
    private ImageView viewImage;

    public HomePageAdapter(Context context, String[] titles, String[] descriptions){
        acontext = context;
        arrayOfTitle = titles;
        arrayOfDescriptions = descriptions;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayOfTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayOfTitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    //convertView hands over control from third activity to third activity single item
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView is the present view that is available
        //if convertView is null it inflates the activity single item -- the cardview into listview
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_homepage_single_item, null);
        }

        theTitle = (TextView)convertView.findViewById(R.id.tvmainText);
        theDescription = (TextView)convertView.findViewById(R.id.tvDescription);
        viewImage = (ImageView)convertView.findViewById(R.id.ivMainImage);

        theTitle.setText(arrayOfTitle[position]);
        theDescription.setText(arrayOfDescriptions[position]);

        if(arrayOfTitle[position].equalsIgnoreCase("Timetable")){
            viewImage.setImageResource(R.drawable.ic_timetable);
        }else if(arrayOfTitle[position].equalsIgnoreCase("Noise Level")){
            viewImage.setImageResource(R.drawable.ic_noiselevel);
        }else if(arrayOfTitle[position].equalsIgnoreCase("Sounds")){
            viewImage.setImageResource(R.drawable.ic_sound);
        }else{
            viewImage.setImageResource(R.drawable.ic_timer);
        }

        return convertView;
    }
}




