package cubex.mahesh.yoursaloon;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (android.os.Build.VERSION.SDK_INT > 21) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");

     TextView wsr = findViewById(R.id.wsr);
        wsr.setTypeface(tf);

    }

  public   void loadMap(View v)
    {
            startActivity(new Intent(this,
                    MapsActivity.class));
    }

}
