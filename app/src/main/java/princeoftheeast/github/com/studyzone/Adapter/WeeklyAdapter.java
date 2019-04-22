package princeoftheeast.github.com.studyzone.Adapter;
/*https://www.youtube.com/watch?v=mw3t-trrnmU&t=587s
The above YouTube tutorial was consulted in creating this adapter
***/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import princeoftheeast.github.com.studyzone.Models.LetterIV;
import princeoftheeast.github.com.studyzone.R;

public class WeeklyAdapter extends ArrayAdapter {

    private int res;
    private LayoutInflater layoutInflater;
    private String[] weekly = new String[]{};

    public WeeklyAdapter(Context context, int resource, String[] objs) {
        super(context, resource, objs);
        this.res = resource;
        this.weekly = objs;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    //a viewholder is like a box that contains the elements - the LetterIV and textview and so whenever the activity loads we need to pass in data to the letter image view and the list view so that it gets loaded into the listview
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){//checks if in initial launch if there is a view..it will be empty on intial launch
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(res, null);// convertView now has the view of the single item
            vh.ivLogo = (LetterIV)convertView.findViewById(R.id.ivWeeklyLetters);
            vh.tvWeekly = (TextView)convertView.findViewById(R.id.tvWeeklyDays);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();//on consecutive launches the holder itself is launched each time
        }

        vh.ivLogo.setOval(true);
        vh.ivLogo.setLetter(weekly[position].charAt(0));//To obtain first letter of the string e.g. M for Monday
        vh.tvWeekly.setText(weekly[position]);
        return convertView;
    }

    class ViewHolder{
        private LetterIV ivLogo;
        private TextView tvWeekly;
    }
}