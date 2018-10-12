package cubex.mahesh.yoursaloon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class SalonRegistration1 extends AppCompatActivity {

    TextView sr,services;
    CircleImageView cview;

    Button register;

    CheckBox makeup,bodycare,hennadesign,haircut,eyebrows,hairprotein,hairstyle,hairtreatment,westernbath,wax,massage,photography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_registration1);

        uploadProfilePic();

        Typeface tf = Typeface.createFromAsset
                (getAssets(),"B93.ttf");

        sr = findViewById(R.id.sr);
        sr.setTypeface(tf);

        services = findViewById(R.id.services);
        services.setTypeface(tf);

        cview = findViewById(R.id.ciview);

        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad =
                        new AlertDialog.Builder(SalonRegistration1.this);
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

        register = findViewById(R.id.register);
        register.setTypeface(tf);

        makeup=findViewById(R.id.makeup);
        makeup.setTypeface(tf);
        bodycare=findViewById(R.id.bodycare);
        bodycare.setTypeface(tf);
        hennadesign=findViewById(R.id.hennadesign);
        hennadesign.setTypeface(tf);
        haircut=findViewById(R.id.haircut);
        haircut.setTypeface(tf);
        eyebrows=findViewById(R.id.eyebrows);
        eyebrows.setTypeface(tf);
        hairprotein=findViewById(R.id.hairprotein);
        hairprotein.setTypeface(tf);
        hairstyle=findViewById(R.id.hairstyles);
        hairstyle.setTypeface(tf);
        hairtreatment=findViewById(R.id.hairtreatment);
        hairtreatment.setTypeface(tf);
        westernbath=findViewById(R.id.westrenbath);
        westernbath.setTypeface(tf);
        wax=findViewById(R.id.wax);
        wax.setTypeface(tf);
        massage=findViewById(R.id.massage);
        massage.setTypeface(tf);
        photography=findViewById(R.id.photography);
        photography.setTypeface(tf);

    }

   public void register(View v)
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase dBase = FirebaseDatabase.getInstance();
        DatabaseReference ref =  dBase.getReference("/users");
        DatabaseReference child_ref = ref.child("/"+uid);
        child_ref.child("makeup").setValue(makeup.isChecked());
        child_ref.child("bodycare").setValue(bodycare.isChecked());
        child_ref.child("hennadesign").setValue(hennadesign.isChecked());
        child_ref.child("haircut").setValue(haircut.isChecked());
        child_ref.child("eyebrows").setValue(eyebrows.isChecked());
        child_ref.child("hairprotein").setValue(hairprotein.isChecked());
        child_ref.child("hairstyle").setValue(hairstyle.isChecked());
        child_ref.child("hairtreatment").setValue(hairtreatment.isChecked());
        child_ref.child("westernbath").setValue(westernbath.isChecked());
        child_ref.child("wax").setValue(wax.isChecked());
        child_ref.child("massage").setValue(massage.isChecked());
        child_ref.child("photography").setValue(photography.isChecked());


        AlertDialog.Builder ad =
                new AlertDialog.Builder(SalonRegistration1.this);
        ad.setTitle("Your Salon");
        ad.setMessage("Your Registration is success,Thank you for registration. ");
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == AlertDialog.BUTTON_POSITIVE) {
                    dialog.dismiss();
                    System.exit(0);
                 } else if (which == AlertDialog.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                }
            }
        };
        ad.setPositiveButton("Ok", listener);
        ad.setNegativeButton("Cancel", listener);
        ad.show();



    }


    void uploadProfilePic( )
    {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("/users/"+uid);
        try {
            FileInputStream fis = openFileInput("salon_profile_pic.png");
            ref.child("salon_profile_pic.png").
                    putStream(fis).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String url =    ref.getDownloadUrl().toString();
                   FirebaseDatabase dBase = FirebaseDatabase.getInstance();
                   DatabaseReference ref =  dBase.getReference("/users");
                   DatabaseReference child_ref = ref.child("/"+uid);
                   child_ref.child("saloon_profile_pic").setValue(url);

               }
           });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void uploadCommercialRegPic( )
    {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("/users/"+uid);
        try {
            FileInputStream fis = openFileInput("salon_commercial_reg_pic.png");
            ref.child("salon_commercial_reg_pic.png").
                    putStream(fis).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String url =    ref.getDownloadUrl().toString();

                            FirebaseDatabase dBase = FirebaseDatabase.getInstance();
                            DatabaseReference ref =  dBase.getReference("/users");
                            DatabaseReference child_ref = ref.child("/"+uid);
                            child_ref.child("salon_commercial_reg_pic").setValue(url);

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode==RESULT_OK)
        {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            cview.setImageBitmap(bmp);


            try {
                FileOutputStream fos = openFileOutput("salon_commercial_reg_pic.png",
                        Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG,
                        100,fos);
                fos.flush();
                fos.close();

                uploadCommercialRegPic();

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

                FileOutputStream fos = openFileOutput("salon_commercial_reg_pic.png",
                        Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG,
                        100,fos);
                fos.flush();
                fos.close();

                uploadCommercialRegPic();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
