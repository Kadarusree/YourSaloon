package cubex.mahesh.yoursaloon;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import cubex.mahesh.yoursaloon.pojos.SaloonPojo;

public class DashboardActivity extends AppCompatActivity {

    static ArrayList<SaloonPojo> saloons_list ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        saloons_list = new ArrayList<>();

        loadApprovedSaloons( );

        if (android.os.Build.VERSION.SDK_INT > 21) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");

     TextView wsr = findViewById(R.id.wsr);
        wsr.setTypeface(tf);

    }


    void loadApprovedSaloons( ){



        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("saloons");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                saloons_list.clear();

                Iterable<DataSnapshot> itrbl = dataSnapshot.getChildren();
                Iterator<DataSnapshot> itr = itrbl.iterator();
                while (itr.hasNext()){

                    DataSnapshot snap = itr.next();

                    Iterable<DataSnapshot> childs_uid = snap.getChildren();

                    Iterator<DataSnapshot> child = childs_uid.iterator();

                    SaloonPojo pojo = new SaloonPojo();

                    while (child.hasNext()){

                        DataSnapshot ds = child.next();

                        /*Toast.makeText(DashboardActivity.this,
                                ds.getKey(),Toast.LENGTH_LONG).show();*/

                        switch (ds.getKey()){
                            case "accepted":
                                pojo.setAccepted(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "bodycare":
                                pojo.setBodycare(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "city":
                                pojo.setCity(ds.getValue().toString());
                                break;
                            case "email":
                                pojo.setEmail(ds.getValue().toString());
                                break;
                            case "eyebrows":
                                pojo.setEyebrows(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "haircut":
                                pojo.setHaircut(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "hairprotein":
                                pojo.setHairprotein(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "hairstyle":
                                pojo.setHairstyle(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "hairtreatment":
                                pojo.setHairtreatment(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "hennadesign":
                                pojo.setHennadesign(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "location":
                                pojo.setLocation(ds.getValue().toString());
                                break;
                            case "makeup":
                                pojo.setMakeup(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "massage":
                                pojo.setMassage(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "password":
                                pojo.setPassword(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "phoneno":
                                pojo.setPhoneno(ds.getValue().toString());
                                break;
                            case "photography":
                                pojo.setPhotography(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "type":
                                pojo.setType(ds.getValue().toString());
                                break;
                            case "wax":
                                pojo.setWax(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                            case "westernbath":
                                pojo.setWesternbath(Boolean.parseBoolean(ds.getValue().toString()));
                                break;
                        }
                    }

                    if(pojo.isAccepted()) {
                        saloons_list.add(pojo);
                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


  public void loadMap(View v)
    {

            String selected_type = "";
            switch (v.getId()){

                case R.id.ic_makeup:
                    selected_type = "ic_makeup";
                    break;
                case R.id.ic_hairstyle:
                    selected_type = "ic_hairstyle";
                    break;
                case R.id.ic_facial:
                    selected_type = "ic_facial";
                    break;
                case R.id.ic_hair_treatment:
                    selected_type = "ic_hair_treatment";
                    break;
                case R.id.ic_henna:
                    selected_type = "ic_henna";
                    break;
                case R.id.ic_bath:
                    selected_type = "ic_bath";
                    break;
                case R.id.ic_haircut:
                    selected_type = "ic_haircut";
                    break;
                case R.id.ic_wax:
                    selected_type = "ic_wax";
                    break;
                case R.id.ic_eyebrows:
                    selected_type = "ic_eyebrows";
                    break;
                case R.id.ic_massage:
                    selected_type = "ic_massage";
                    break;
                case R.id.ic_hair_protien:
                    selected_type = "ic_hair_protien";
                    break;
                case R.id.ic_photograpgy:
                    selected_type = "ic_photograpgy";
                    break;

            }
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("selected_type",selected_type);
            startActivity(i);

    }

}
