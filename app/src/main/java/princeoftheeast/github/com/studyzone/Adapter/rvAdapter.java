package princeoftheeast.github.com.studyzone.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import princeoftheeast.github.com.studyzone.R;
import princeoftheeast.github.com.studyzone.Models.Subject;

/**
 * Created by M Chowdhury on 13/02/2019.
 */

public class rvAdapter extends RecyclerView.Adapter<rvHolders> {
    private List<Subject> subject;
    protected Context context;

    public rvAdapter(Context context, List<Subject> subject) {
        this.subject = subject;
        this.context = context;
    }
    @Override
    public rvHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_day_single_item, parent, false);
        rvHolders viewHolder = new rvHolders(layoutView, subject);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(rvHolders holder, int position) {
        holder.entryTitle.setText(subject.get(position).getSubject());
        holder.entryTime.setText(subject.get(position).getSubjectTime());

    }
    @Override
    public int getItemCount() {
        return this.subject.size();
    }


}

