package cubex.mahesh.yoursaloon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerRegistration extends AppCompatActivity {

    TextView sr;
    CircleImageView cview;

    EditText email, pass, phno, city;

    Button next;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        mAuth = FirebaseAuth.getInstance();


        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");

        sr = findViewById(R.id.sr);
        sr.setTypeface(tf);

        cview = findViewById(R.id.ciview);
        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad =
                        new AlertDialog.Builder(CustomerRegistration.this);
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

        next = findViewById(R.id.next);
        next.setTypeface(tf);

    }

   public void submit(View v)
    {

//    startActivity(new Intent(this,
//                    SalonRegistration1.class));

        mAuth.createUserWithEmailAndPassword(
                email.getText().toString(),
                pass.getText().toString()).addOnCompleteListener((task)->{
            if(task.isSuccessful()){

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                FirebaseDatabase dBase = FirebaseDatabase.getInstance();
                DatabaseReference ref =  dBase.getReference("/users");
                DatabaseReference child_ref = ref.child("/"+uid);
                child_ref.child("reg_type").setValue("customer");
                child_ref.child("email").setValue(email.getText().toString());
                child_ref.child("password").setValue(pass.getText().toString());
                child_ref.child("phoneno").setValue(phno.getText().toString());
                child_ref.child("city").setValue(city.getText().toString());

                uploadProfilePic();

//                startActivity(new Intent(this,
//                        SalonRegistration1.class));

                startActivity(new Intent(this,
                        DashboardActivity.class));

            }else{

                Toast.makeText(CustomerRegistration.this,
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
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            cview.setImageBitmap(bmp);


            try {
                FileOutputStream fos = openFileOutput("customer_profile_pic.png",
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

                FileOutputStream fos = openFileOutput("customer_profile_pic.png",
                        Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG,
                        100,fos);
                fos.flush();
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    void uploadProfilePic( )
    {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("/users/"+uid);
        try {
            FileInputStream fis = openFileInput("customer_profile_pic.png");
            ref.child("customer_profile_pic.png").
                    putStream(fis).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String url =    ref.getDownloadUrl().toString();
                            FirebaseDatabase dBase = FirebaseDatabase.getInstance();
                            DatabaseReference ref =  dBase.getReference("/users");
                            DatabaseReference child_ref = ref.child("/"+uid);
                            child_ref.child("customer_profile_pic").setValue(url);

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
