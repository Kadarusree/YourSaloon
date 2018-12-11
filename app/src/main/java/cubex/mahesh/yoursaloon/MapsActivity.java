package cubex.mahesh.yoursaloon;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cubex.mahesh.yoursaloon.pojos.SaloonPojo;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String selected_type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        selected_type = getIntent().getStringExtra("selected_type");


        if (android.os.Build.VERSION.SDK_INT > 21) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(24.774265, 46.738586);
        mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Saudi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                loc,10));*/

        switch (selected_type) {
            case "ic_makeup":
                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isMakeup()){
                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,15));
                        }
                    }
                }
                break;
            case "ic_hairstyle":
                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isHairstyle()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                           // mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc_obj,17));
                          //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
                        }

                    }
                }
                break;
            case "ic_facial":  //bodycare
                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isBodycare()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_hair_treatment":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isHairtreatment()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_henna":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isHennadesign()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_bath":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isWesternbath()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }


                break;
            case "ic_haircut":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isHaircut()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_wax":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isWax()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_eyebrows":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isEyebrows()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_massage":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isMassage()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_hair_protien":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isHairprotein()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
            case "ic_photograpgy":

                for(SaloonPojo pojo:DashboardActivity.saloons_list){
                    if(pojo.isPhotography()){

                        String loc = pojo.getLocation();
                        if(loc.contains(",")){
                            String[ ] values = loc.split(",");
                            double lati = Double.parseDouble(values[0]);
                            double longi = Double.parseDouble(values[1]);
                            LatLng loc_obj = new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(loc_obj).title(pojo.getEmail()+"\n"+pojo.getPhoneno()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_obj));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    loc_obj,10));
                        }

                    }
                }

                break;
        }
    }
}
