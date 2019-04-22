package princeoftheeast.github.com.studyzone.UI;
/*
https://www.youtube.com/watch?v=pFc59hCnbPQ
Videos from the above Youtube tutorial series were used in constructing this class
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import princeoftheeast.github.com.studyzone.R;

public class MainActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText UserPassword;
    private TextView UserInformation;
    private Button UserLogin;
    private TextView userRegistration;
    private FirebaseAuth fbAuth;

    private ProgressDialog pDialog;
    private int counter = 5;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assiging variables with xml layout ids
        UserName = (EditText)findViewById(R.id.etRegEmail);
        UserPassword = (EditText)findViewById(R.id.etRegPassword);
        UserInformation = (TextView)findViewById(R.id.tvUserLogInfo);
        UserLogin = (Button)findViewById(R.id.userLoginBtn);
        userRegistration = (TextView)findViewById(R.id.tvUserRegister);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        UserInformation.setText("No of attempts remaining: 5");

        fbAuth = FirebaseAuth.getInstance();
        pDialog = new ProgressDialog(this);//get an instance of the progress dialog

        FirebaseUser firebaseUser = fbAuth.getCurrentUser();//checks with the database if a user has already logged into the app

        //if user has logged in they are directed to the SecondActivity
        if(firebaseUser != null){
           finish();
           startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        }

        //setting an onclicklistener for the login button
        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(UserName.getText().toString(), UserPassword.getText().toString());//convert whatever is in the editText box in the xml into a atring
            }
        });
        //when user clicks on 'tvUserRegisiter' they are directed to RegisterActviity class
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validateUser(String userName, String userPassword){
        if(userName.equals("") || userPassword.equals("")) return;

        pDialog.setMessage("we are working on getting you started");
        pDialog.show();

        //User is signed in using userName and UserPassword and addOnCompleteListener((task) will check if task was successful
        fbAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pDialog.dismiss();//hide the progress dialog

                if(task.isSuccessful()){
                    checkUserEmailVerification();//a method to check if the user has verified their email
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    UserInformation.setText("No Of Attempts Remaining " + counter);
                    if(counter == 0){
                        UserLogin.setEnabled(false);//login button is disabled
                    }
                }
            }
        });
    }

    public void checkUserEmailVerification(){
        FirebaseUser fbUser = fbAuth.getInstance().getCurrentUser();//first getting the instance of the firbase authentication and then getting the user
        boolean emailCheck = fbUser.isEmailVerified();//check if user has verified their email

        //if user has verified their email they are redirected to the secondActivity
        if(emailCheck){
            finish();
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        }
        //if user has not verified their email they are logged out
        else{
            Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
            fbAuth.signOut();
        }
    }

}
