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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import princeoftheeast.github.com.studyzone.R;

public class PasswordActivity extends AppCompatActivity {

    private EditText emailPassword;
    private Button resetPassword;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailPassword = (EditText)findViewById(R.id.etForgotPasswordEmail);
        resetPassword = (Button)findViewById(R.id.btnResetPassword);
        fbAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailPassword.getText().toString().trim();

                //if email edittext field is empty ask used to enter email
                if(userEmail.equals("")) {
                    Toast.makeText(PasswordActivity.this, "Please enter your email ", Toast.LENGTH_SHORT).show();
                }else{
                    //send user a reset link for their account and direct user to MainActivity login page
                    fbAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Email for password reset sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(PasswordActivity.this, "Error could not send password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
