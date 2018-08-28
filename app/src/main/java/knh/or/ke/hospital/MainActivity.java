package knh.or.ke.hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button signinbtn, signupbtn;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 123;
    private static final String SIGN_IN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            Intent mybookings=new Intent(MainActivity.this, MyBookings.class);
            startActivity(mybookings);
            finish();
        }
        setContentView(R.layout.activity_main);

        signinbtn=findViewById(R.id.signinbtn);
        signupbtn=findViewById(R.id.signupbtn);

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signinclass=new Intent(MainActivity.this, Signin.class);
                startActivity(signinclass);
                finish();

            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTosUrl(SIGN_IN)
                                .build(),
                        RC_SIGN_IN);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Intent mybookings=new Intent(MainActivity.this, MyBookings.class);
                startActivity(mybookings);
                finish();
            }
            if(requestCode== RESULT_CANCELED){

                Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
            }
            return;
        }
        Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
    }
}
