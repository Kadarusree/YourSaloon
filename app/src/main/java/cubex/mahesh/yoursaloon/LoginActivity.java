package cubex.mahesh.yoursaloon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView li,fp;
    EditText email,pwd;
    CheckBox cb1;
    Button login, caft;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");

        li = findViewById(R.id.li);
        li.setTypeface(tf);


        email = findViewById(R.id.email);
        email.setTypeface(tf);

        pwd = findViewById(R.id.password);
        pwd.setTypeface(tf);

        cb1 = findViewById(R.id.cb1);
        cb1.setTypeface(tf);

        fp = findViewById(R.id.fp);
        fp.setTypeface(tf);

        login = findViewById(R.id.login);
        login.setTypeface(tf);

        caft = findViewById(R.id.caft);
        caft.setTypeface(tf);

        mAuth = FirebaseAuth.getInstance();



        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {





            }
        });

        login.setOnClickListener((v)->{

            mAuth.signInWithEmailAndPassword(email.getText().toString(),
                    pwd.getText().toString()).addOnCompleteListener((task)->{

                        if(task.isSuccessful()){

                     startActivity(new Intent(LoginActivity.this,
                             DashboardActivity.class));


                        }else{

                            Toast.makeText(LoginActivity.this,
                                    "Please provide valid credentials",
                                    Toast.LENGTH_LONG).show();

                        }

            });
        });

        caft.setOnClickListener((v)->{

            SharedPreferences spf =
                    getSharedPreferences("saloon_prfs", Context.MODE_PRIVATE);
        String type =  spf. getString("user_type","");

        if(type.equals("salon")) {
            startActivity(new Intent(LoginActivity.this,
                    SalonRegistration.class));
        }else   if(type.equals("business women")) {
            startActivity(new Intent(LoginActivity.this,
                    BusinessWomanRegistration.class));
        }else   if(type.equals("business guest")) {
            startActivity(new Intent(LoginActivity.this,
                    BusinessGuestRegistration.class));
        }else   if(type.equals("customer")) {
            startActivity(new Intent(LoginActivity.this,
                    CustomerRegistration.class));
        }
        });


    }
}
