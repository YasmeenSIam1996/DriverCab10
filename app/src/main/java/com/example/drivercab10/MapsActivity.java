package com.example.drivercab10;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.drivercab10.API.ResponseOrderDetails;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.Interfaces.UniversalStringCallBack;
import com.example.drivercab10.util.AddLineToMap;
import com.example.drivercab10.util.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AddLineToMap addLineToMap;
    private String duration = "", distance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addLineToMap = new AddLineToMap(mMap, MapsActivity.this);
        ResponseOrderDetails.Details details = (ResponseOrderDetails.Details) getIntent().getSerializableExtra("Details");
        Log.e("REQUEST_DETAILS0", details.toString());
        TimeTrackingOrder(details.getS_address(), details.getD_address(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                        + details.getS_latitude() + "," + details.getS_longitude() + "&" + Constants.DESTINATION + "="
                        + details.getD_latitude() + "," + details.getD_longitude() + "&" + Constants.MODE + "="
                        + "driving" + "&" + Constants.LANGUAGE + "="
                        + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                        + String.valueOf(false),
                new LatLng(details.getS_latitude(), details.getS_longitude()), new LatLng(details.getD_latitude(), details.getD_longitude()), MapsActivity.this);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(details.getS_latitude(), details.getS_longitude()));
        builder.include(new LatLng(details.getD_latitude(), details.getD_longitude()));
        LatLngBounds bounds = builder.build();

        // begin new code:
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        // end of new code

        googleMap.animateCamera(cu);
    }

    public void TimeTrackingOrder(final String address, final String address2, String link, final LatLng LatLangRes, final LatLng LatLangDriver, final Context context) {

        new UserAPI().TimeOrderTracking(link, new UniversalStringCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("result1_result1", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("OK")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        JSONObject elementsObject = jsonArray.getJSONObject(0);
                        JSONArray elementsArray = elementsObject.getJSONArray("elements");
                        JSONObject distanceObject = elementsArray.getJSONObject(0);
                        JSONObject durationObject = distanceObject.getJSONObject("duration");
                        duration = durationObject.getString("text");
                        JSONObject distanceInnerObject = distanceObject.getJSONObject("distance");
                        distance = distanceInnerObject.getString("text");

                        mMap.clear();

                        runOnUiThread(new Runnable() {
                            public void run() {

                                mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));
                                mMap.addMarker(new MarkerOptions().position(LatLangDriver).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address2, "1")))));


                            }
                        });


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String result) {
                if (result != null) {
                    Constants.showDialog((Activity) context, result);
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

}