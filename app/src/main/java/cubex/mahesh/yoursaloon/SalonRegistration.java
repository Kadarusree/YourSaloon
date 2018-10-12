package cubex.mahesh.yoursaloon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SalonRegistration extends AppCompatActivity {

    TextView sr;
    CircleImageView cview;

    EditText email, pass, phno, city, location;

    Button next;

    private FirebaseAuth mAuth;

    private  Button location_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_registration);

        mAuth = FirebaseAuth.getInstance();

        Typeface tf = Typeface.createFromAsset
                (getAssets(), "B93.ttf");

        sr = findViewById(R.id.sr);
        sr.setTypeface(tf);

        cview = findViewById(R.id.ciview);

        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad =
                        new AlertDialog.Builder(SalonRegistration.this);
                ad.setTitle("Your Salon");
                ad.setMessage("Please select Image source.");
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == AlertDialog.BUTTON_POSITIVE) {
                            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivityForResult(i, 123);
                        } else if (which == AlertDialog.BUTTON_NEGATIVE) {

                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_GET_CONTENT);
                            i.setType("image/*");
                            startActivityForResult(i, 124);

                        } else if (which == AlertDialog.BUTTON_NEUTRAL) {
                            dialog.cancel();
                        }
                    }
                };
                ad.setPositiveButton("Camera", listener);
                ad.setNegativeButton("Gallery", listener);
                ad.setNeutralButton("Cancel", listener);
                ad.show();
            }
        });


        email = findViewById(R.id.email);
        email.setTypeface(tf);

        pass = findViewById(R.id.pass);
        pass.setTypeface(tf);

        phno = findViewById(R.id.phno);
        phno.setTypeface(tf);

        city = findViewById(R.id.city);
        city.setTypeface(tf);


        location = findViewById(R.id.location);
        location.setTypeface(tf);

        location_picker = findViewById(R.id.location_picker);
        location_picker.setOnClickListener((v)->{

            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent i = builder.build(SalonRegistration.this);
                startActivityForResult(i,150);
            }catch (Exception e){
                e.printStackTrace();
            }
        });


        next = findViewById(R.id.next);
        next.setTypeface(tf);

        LocationManager lManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }else {
            Location l = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 1, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            double lati = location.getLatitude();
                            double longi = location.getLongitude();

                            SalonRegistration.this.location.setText(lati+","+longi);

                            lManager.removeUpdates(this);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });
    }

    }

   public void next(View v)
    {
        mAuth.createUserWithEmailAndPassword(
                email.getText().toString(),
                pass.getText().toString()).addOnCompleteListener((task)->{
                    if(task.isSuccessful()){

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        FirebaseDatabase dBase = FirebaseDatabase.getInstance();
                        DatabaseReference ref =  dBase.getReference("/users");
                        DatabaseReference child_ref = ref.child("/"+uid);
                        child_ref.child("reg_type").setValue("salon");
                        child_ref.child("email").setValue(email.getText().toString());
                        child_ref.child("password").setValue(pass.getText().toString());
                        child_ref.child("phoneno").setValue(phno.getText().toString());
                        child_ref.child("city").setValue(city.getText().toString());
                        child_ref.child("location").setValue(location.getText().toString());

                        startActivity(new Intent(this,
                                SalonRegistration1.class));


                    }else{

                        Toast.makeText(SalonRegistration.this,
                                "Failed to Register, May be Email id is already exist!",
                                Toast.LENGTH_LONG).show();

                    }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode==RESULT_OK)
        {
            Bitmap  bmp = (Bitmap) data.getExtras().get("data");
            cview.setImageBitmap(bmp);


            try {
                FileOutputStream fos = openFileOutput("salon_profile_pic.png",
                        Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG,
                        100,fos);
                fos.flush();
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(requestCode==124 && resultCode==RESULT_OK)
        {
            try {

            Uri u = data.getData();
            cview.setImageURI(u);
            Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);

                FileOutputStream fos = openFileOutput("salon_profile_pic.png",
                        Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG,
                        100,fos);
                fos.flush();
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(requestCode==150 && resultCode==RESULT_OK)
        {
            Place selectedPlace = PlacePicker.getPlace(data, this);

            double lati = selectedPlace.getLatLng().latitude;
            double longi = selectedPlace.getLatLng().latitude;

            location.setText(lati+","+longi);

        }

    }

}
