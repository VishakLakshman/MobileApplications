package com.example.mypc.session3;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    LocationManager mLocationMgr;

    ArrayList<Deal> route;
    ArrayList<LatLng> routeLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        route = new ArrayList<>();
        routeLatLng = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            HashMap<String,Deal> asd = (HashMap<String,Deal>) getIntent().getExtras().getSerializable("route");
            route.addAll(asd.values());
            Log.d("Route", route.toString());
            if(route.size()>0)
            {
                routeLatLng = prepareRoute(route);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }

        mLocationMgr= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    double circleRad = 1*1000;

    private int getZoomLevel(double radius){
        double scale = radius / 500;
        return ((int) (16 - Math.log(scale) / Math.log(2)));
    }


    private ArrayList<LatLng> prepareRoute(ArrayList<Deal> deals)
    {
        ArrayList<LatLng> result = new ArrayList<>();

        for(int i=0;i<deals.size();i++)
        {
            Deal deal = deals.get(i);
            LatLng latLng = new LatLng(deal.getLocation().getLat(),deal.getLocation().getLon());
            result.add(latLng);
        }

        return result;
    }

    Marker mark1,mark2;

    Marker tempMarker = null;

    Polyline polyline;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        try {
            mMap.setMyLocationEnabled(true);
        }
        catch (SecurityException e)
        {

        }
        mMap.getUiSettings().setCompassEnabled(true);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location=null;
        try {
            location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, true));
        }
        catch (SecurityException e)
        {

        }
        double latitude;
        double longitude;

        LatLng sydney;
        if(location!=null)
        {
          latitude = location.getLatitude();
            longitude = location.getLongitude();
            /*latitude = 35.330153;
            longitude = -80.732529;*/

        }
        else
        {
            latitude = 35.330153;
            longitude = -80.732529;
        }
        sydney = new LatLng(latitude, longitude);

        mark1 = mMap.addMarker(new MarkerOptions().position(sydney).title("Start"));
        tempMarker = mark1;

        for(int i=0;i<routeLatLng.size();i++)
        {
            String start,end;
            if(mark2!=null) {
                tempMarker = mark2;
                mark2.remove();
            }
            mark2= mMap.addMarker(new MarkerOptions().position(routeLatLng.get(i)).title("Destination"));

            start = tempMarker.getPosition().latitude+","+tempMarker.getPosition().longitude;
            end = mark2.getPosition().latitude+","+mark2.getPosition().longitude;

            new GetDirection().execute(start,end);

            if(i+1 == routeLatLng.size())
            {
                tempMarker = mark2;
                mark2 = mark1;

                start = tempMarker.getPosition().latitude+","+tempMarker.getPosition().longitude;
                end = mark2.getPosition().latitude+","+mark2.getPosition().longitude;

                new GetDirection().execute(start,end);

            }

        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setContentView(R.layout.activity_maps);

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);

                } else {
                    // permission was denied
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not activated")
                    .setMessage("Wanna activate it?")
                    .setPositiveButton("Activate",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which)
                        {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Deactivate",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which)
                        {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    Double rad(Double x)
    {
        return x*Math.PI /100;
    }

    Double getDistance(LatLng l1, LatLng l2)
    {
        Double R = 6378137.0;
        Double dLat = rad(l2.latitude - l1.latitude);
        Double dLong = rad(l2.longitude - l1.longitude);
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(l1.latitude)) * Math.cos(rad(l2.latitude)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = R * c;
        return d;//answer is in meters
    }


    class GetDirection extends AsyncTask<String, String, String> {


        String origin = "37.7749,-122.4194";
        String destination = "32.7157,-117.1611";

        List<LatLng> pontos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Status"," started");
            pontos = new ArrayList<>();
        }

        protected String doInBackground(String... args) {
            origin = args[0];
            destination = args[1];
            String stringUrl = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"&destination="+destination+"&key=AIzaSyD-8G4y2JhHyUk8m-MvnrfHrJ1LDNDYz7M";
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(stringUrl);
                Log.d("Status"," inbackground");
                HttpURLConnection httpconn = (HttpURLConnection) url
                        .openConnection();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(httpconn.getInputStream()),
                            8192);
                    String strLine = null;

                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                }

                Log.d("Status",response.toString());

                String jsonOutput = response.toString();

                JSONObject jsonObject = new JSONObject(jsonOutput);

                // routesArray contains ALL routes
                JSONArray routesArray = jsonObject.getJSONArray("routes");

                // Grab the first route
                JSONObject route = routesArray.getJSONObject(0);

                JSONObject poly = route.getJSONObject("overview_polyline");
                String polyline = poly.getString("points");
                pontos = decodePoly(polyline);
                Log.d("asda",pontos.toString());

            } catch (Exception e) {

            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            for (int i = 0; i < pontos.size() - 1; i++) {
                LatLng src = pontos.get(i);
                LatLng dest = pontos.get(i + 1);
                if(i==0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(src));

                    float zoomLevel = getZoomLevel(circleRad);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(src, zoomLevel));
                }
                try{
                    //here is where it will draw the polyline in your map
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude,                dest.longitude))
                            .width(5).color(Color.RED).geodesic(true));
                }catch(NullPointerException e){
                    Log.e("Error", "NullPointerException onPostExecute: " + e.toString());
                }catch (Exception e2) {
                    Log.e("Error", "Exception onPostExecute: " + e2.toString());
                }

            }

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}
