package princeoftheeast.github.com.studyzone.UI;
/*
https://www.youtube.com/watch?v=pFc59hCnbPQ
Videos from the above Youtube tutorial series were used in constructing this class
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import princeoftheeast.github.com.studyzone.Models.UserProfile;
import princeoftheeast.github.com.studyzone.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regPassword, regEmail;
    private Button regButton;
    private TextView alreadySignedUp;
    private FirebaseAuth fbAuth;

    private String userName, userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();//variables are assigned with this method

        fbAuth = FirebaseAuth.getInstance();//to get an instance of the authenticator into the variable

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload on to the database
                    String userReg_email = regEmail.getText().toString().trim();//convert email to string ridding of white spsaces
                    String userReg_password = regPassword.getText().toString().trim();

                    //first line will add user email and password to database
                    fbAuth.createUserWithEmailAndPassword(userReg_email, userReg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendData();
                                sendingEmailVerification();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        //if user clicks on 'Already Signed in? Login' then direct user to MainActivity page
        alreadySignedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    //assinging varaible to xml layout ids
    private void setupUIViews(){
        regName = (EditText)findViewById(R.id.etRegName);
        regEmail = (EditText)findViewById(R.id.etRegEmail);
        regPassword = (EditText)findViewById(R.id.etRegPassword);
        regButton = (Button)findViewById(R.id.btnUserRegister);
        alreadySignedUp = (TextView)findViewById(R.id.tvLogin);
    }

    private boolean validate(){
        Boolean result = false;

        userName = regName.getText().toString();
        userPassword = regPassword.getText().toString();
        userEmail = regEmail.getText().toString();

        //if any of the text fields are empty show toast message and return result as false
        if(userName.isEmpty() || userPassword.isEmpty() || userEmail.isEmpty()){
            Toast.makeText(this, "Pelase fill in all the information ", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    public void sendingEmailVerification(){
        final FirebaseUser fbUser = fbAuth.getCurrentUser();
        if(fbUser!=null){
            //first line sends user a verification email
            fbUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registration successful, Verification email sent", Toast.LENGTH_SHORT).show();
                        fbAuth.signOut();//sign out user as they need to verify their email
                        finish();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));//send user back to login page
                    }else{
                        Toast.makeText(RegisterActivity.this, "Verification email not sent", Toast.LENGTH_SHORT).show();//when firebase server or internet is down
                    }
                }
            });
        }
    }

    private void sendData(){
        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = fbDatabase.getReference(fbAuth.getUid());//in firebase every user has unique uid
        UserProfile uProfile = new UserProfile(userName, userEmail);
        myRef.setValue(uProfile);

    }
}
