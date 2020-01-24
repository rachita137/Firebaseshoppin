package tatastrive.application.firebaseshoppin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView signUp;

//firebase authentication
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//return correspoinding to default firebase instance
        mAuth=FirebaseAuth.getInstance();
//returns sign in
        if (mAuth.getCurrentUser()!=null){
           // already log then move to other activity
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }


        mDialog=new ProgressDialog(this);



        email=findViewById(R.id.email_login);
        pass=findViewById(R.id.password_login);

        btnLogin=findViewById(R.id.btn_login);
        signUp=findViewById(R.id.signup_txt);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String mEmail=email.getText().toString().trim();
              String mPass=pass.getText().toString().trim();

              if (TextUtils.isEmpty(mEmail)){
                  email.setError("Required Field..");
                  return;
              }
              if (TextUtils.isEmpty(mPass)){
                  pass.setError("Required Field..");
                  return;
              }

              mDialog.setMessage("Processing..");
              mDialog.show();

//automatically generate means predfined function use.
              mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()){
                          startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                          Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

                          mDialog.dismiss();
                      }
                      else {

                          Toast.makeText(getApplicationContext(),"Failed.",Toast.LENGTH_SHORT).show();
                          mDialog.dismiss();
                      }

                  }
              });


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

    }
}
