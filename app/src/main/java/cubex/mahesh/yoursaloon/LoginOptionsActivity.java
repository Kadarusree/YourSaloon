package cubex.mahesh.yoursaloon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOptionsActivity extends AppCompatActivity {
    Button salon,cust,bwoman,bguest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        salon = findViewById(R.id.sln_btn);
        cust = findViewById(R.id.cust_btn);
        bwoman = findViewById(R.id.bwmn_btn);
        bguest = findViewById(R.id.bns_bgst);

        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");
        salon.setTypeface(tf);
        cust.setTypeface(tf);
        bwoman.setTypeface(tf);
        bguest.setTypeface(tf);
    }

    public void salon(View v)
    {
        SharedPreferences spf =
    getSharedPreferences("saloon_prfs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = spf.edit();
        switch (v.getId())
        {
            case R.id.sln_btn:

                spe.putString("user_type","salon");

                startActivity(new Intent(this,
             LoginActivity.class));

                break;

            case R.id.cust_btn:
                spe.putString("user_type","customer");

                startActivity(new Intent(this,
                        LoginActivity.class));

                break;
            case R.id.bwmn_btn:
                spe.putString("user_type","business women");

                startActivity(new Intent(this,
                        LoginActivity.class));

                break;
            case R.id.bns_bgst:
                spe.putString("user_type","business guest");

                startActivity(new Intent(this,
                        LoginActivity.class));

                break;

        }
        spe.commit();
    }
}
