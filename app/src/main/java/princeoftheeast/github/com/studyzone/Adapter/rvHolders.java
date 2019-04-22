package princeoftheeast.github.com.studyzone.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import princeoftheeast.github.com.studyzone.R;
import princeoftheeast.github.com.studyzone.Models.Subject;

import static princeoftheeast.github.com.studyzone.UI.WeeklyActivity.SELECTED_DAY;

public class rvHolders extends RecyclerView.ViewHolder {
    private static final String TAG = rvHolders.class.getSimpleName();
    public ImageView checkIcon;
    public TextView entryTitle;
    public TextView entryTime;
    public Button cancelNotification;
    public TextView notificationStatus;
    public ImageView deleteIcon;
    private List<Subject> entryObject;
    private FirebaseAuth fbAuth;
    private DatabaseReference databaseReference;

    public rvHolders(final View itemView, final List<Subject> entryObject) {
        super(itemView);
        this.entryObject = entryObject;

        fbAuth = FirebaseAuth.getInstance();

        entryTitle = (TextView) itemView.findViewById(R.id.tvSubjectTitle);
        entryTime = (TextView) itemView.findViewById(R.id.tvSubjectTime);
        checkIcon = (ImageView) itemView.findViewById(R.id.ivCheck);
        deleteIcon = (ImageView) itemView.findViewById(R.id.ivDeleteEntry);
        cancelNotification = (Button) itemView.findViewById(R.id.btnCancelNotification);
        notificationStatus = (TextView) itemView.findViewById(R.id.tvNotificationStatus);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Delete has been clicked", Toast.LENGTH_LONG).show();
                String subjectTitle = entryObject.get(getAdapterPosition()).getSubject();
                Log.d(TAG, "Subject Title " + subjectTitle);
                databaseReference = FirebaseDatabase.getInstance().getReference(fbAuth.getUid()).child(SELECTED_DAY);
                Query dataQuery = databaseReference.orderByChild("subject").equalTo(subjectTitle);
                dataQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataQuery : dataSnapshot.getChildren()) {
                            dataQuery.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                cancelNotification();
            }
        });

        cancelNotification.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                cancelNotification();
            }
        });


    }

    private void cancelNotification(){
        AlarmManager alarmManager = (AlarmManager) itemView.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(itemView.getContext(), NotificationReceiver.class);
        //use getApaterPosition() in the request code arugment as this will make sure the right alarm gets cancelled
        PendingIntent pendingIntent = PendingIntent.getBroadcast(itemView.getContext(), getAdapterPosition(), intent, 0);

        alarmManager.cancel(pendingIntent);
        notificationStatus.setText("Notification canceled");
        cancelNotification.setVisibility(View.INVISIBLE);
    }


}