package com.example.drivercab10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivercab10.API.ResponseAccept;
import com.example.drivercab10.API.ResponseCancel;
import com.example.drivercab10.API.ResponseCheckStatus;
import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseOnLine;
import com.example.drivercab10.API.ResponseProviderStarted;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.FirebaseUtils.MyFirebaseMessagingService;
import com.example.drivercab10.Interfaces.ObjectClickListener;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.Interfaces.UniversalStringCallBack;
import com.example.drivercab10.model.OrderNotification;
import com.example.drivercab10.util.AddLineToMap;
import com.example.drivercab10.util.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String duration = "", distance = "";

    ImageView imgNanDrawer;
    DrawerLayout drawer;
    TextView tvTitleToolbar, tvAvailable;
    private Switch switchAvailable;

    View itemNavHome, itemNavYourTrips, itemNavFinance, itemNavUpload, itemNavHelp, itemNavLogout;
    private float zoom = 15.0f;
    private AddLineToMap addLineToMap;
    private ResponseAccept.Accept accept;
    //Sheet Layout
    private View rest_bottom_layout;
    private ImageView imgUserSheet, goPlaces;
    private TextView UserNameSheet, PhoneNumberSheet, CallUserSheet;
    private RatingBar UserRatingSheet;
    private Button ConfirmStartingSheet, CancelTripSheet;
    private String UserMobile = "";
    private int RequestIdTemp = 0;
    private int second = 0;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private boolean FirstTime = true;
    private ResponseCheckStatus.CheckStatus checkStatus;
    private RatingBar DriverRate;
    private ImageView DriverImage;
    private TextView DriverName;
    private boolean isStepTwo = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        findViews();
        getOnlineStatus();
        MyFirebaseMessagingService.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(OrderNotification orderNotification) {
                Log.e("Status_id", orderNotification.getStatus_id() + "");
                if (Integer.valueOf(orderNotification.getStatus_id()) == 2) {
                    Intent intent = new Intent(HomeActivity.this, OrderCounterActivity.class);
                    intent.putExtra("OrderNotification", orderNotification);
                    startActivity(intent);
                    finish();
                }
            }
        });

        if (getIntent().getStringExtra("message") != null) {
            Constants.showDialog(HomeActivity.this, getIntent().getStringExtra("message"));
        }

        SupportMapFragment mapHome = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapHome);
        mapHome.getMapAsync(this);

    }


    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);


            Log.e("_second_", second + "");
            if (second == 0 || second == 7) {
                if (locationResult.getLastLocation() == null)
                    return;
                currentLocation = locationResult.getLastLocation();
                Log.e("_second_1", currentLocation.getLongitude() + "");
                Log.e("_second_2", currentLocation.getLatitude() + "");

                changeLocationFirebase(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                if (firstTimeFlag && mMap != null) {
                    animateCamera(currentLocation);
                    firstTimeFlag = false;
                }
                getCurrentLocation();
            }
            second++;

            if (second == 7) {
                second = 0;
            }

        }
    };

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private void changeLocationFirebase(LatLng latLng) {
        int Id = ApplicationController.getInstance().getUser().getId();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("drivers/" + Id + "/lat");
        DatabaseReference myRef2 = database.getReference("drivers/" + Id + "/lng");
        if (latLng.latitude != 0 || latLng.longitude != 0) {
            myRef2.setValue(latLng.longitude);
            myRef.setValue(latLng.latitude);
        }
    }

    private void findViews() {
        imgUserSheet = findViewById(R.id.imgUserSheet);
        goPlaces = findViewById(R.id.goPlaces);
        UserNameSheet = findViewById(R.id.UserNameSheet);
        UserRatingSheet = findViewById(R.id.UserRatingSheet);
        PhoneNumberSheet = findViewById(R.id.PhoneNumberSheet);
        CallUserSheet = findViewById(R.id.CallUserSheet);
        ConfirmStartingSheet = findViewById(R.id.ConfirmStartingSheet);
        CancelTripSheet = findViewById(R.id.CancelTripSheet);
        DriverRate = findViewById(R.id.DriverRate);
        DriverImage = findViewById(R.id.DriverImage);
        DriverName = findViewById(R.id.DriverName);
//        DriverRate.setRating(ApplicationController.getInstance().getUser().get);
        DriverName.setText(ApplicationController.getInstance().getUser().getName());
        Picasso.with(HomeActivity.this).load(ApplicationController.getInstance().getUser().getPicture()).fit()
                .into(DriverImage);

        imgNanDrawer = findViewById(R.id.imgNavDrawer);
        drawer = findViewById(R.id.drawer);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getResources().getString(R.string.navMain));

        //nav drawer items
        itemNavHome = findViewById(R.id.itemNavHome);
        itemNavYourTrips = findViewById(R.id.itemNavYourTrips);
        itemNavFinance = findViewById(R.id.itemNavFinance);
        itemNavUpload = findViewById(R.id.itemNavUpload);
        itemNavHelp = findViewById(R.id.itemNavHelp);
        switchAvailable = findViewById(R.id.switchAvailable);
        tvAvailable = findViewById(R.id.tvAvailable);
        itemNavLogout = findViewById(R.id.itemNavLogout);
        rest_bottom_layout = findViewById(R.id.rest_bottom_layout);

        itemNavHome.setOnClickListener(this);
        itemNavYourTrips.setOnClickListener(this);
        itemNavFinance.setOnClickListener(this);
        itemNavUpload.setOnClickListener(this);
        itemNavHelp.setOnClickListener(this);
        itemNavLogout.setOnClickListener(this);
        ConfirmStartingSheet.setOnClickListener(this);
        CancelTripSheet.setOnClickListener(this);
        CallUserSheet.setOnClickListener(this);
        goPlaces.setOnClickListener(this);
        switchAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvAvailable.setText(getResources().getString(R.string.available));
                    setOnlineStatus(1);
                } else {
                    tvAvailable.setText(getResources().getString(R.string.unavailable));
                    setOnlineStatus(0);

                }
            }
        });

        imgNanDrawer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNavDrawer:
                drawer.openDrawer(Gravity.START);
                break;

            case R.id.itemNavHome:
                drawer.closeDrawer(Gravity.START);
                break;

            case R.id.itemNavYourTrips:
                drawer.closeDrawer(Gravity.START);
                startActivity(new Intent(this, YourTripsActivity.class));
                break;

            case R.id.itemNavFinance:
                drawer.closeDrawer(Gravity.START);
                startActivity(new Intent(this, FinanceActivity.class));
                break;

            case R.id.itemNavUpload:
                drawer.closeDrawer(Gravity.START);
                startActivity(new Intent(this, UploadFilesActivity.class));
                break;

            case R.id.itemNavHelp:
                drawer.closeDrawer(Gravity.START);
                startActivity(new Intent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))));
                break;
            case R.id.itemNavLogout:
                drawer.closeDrawer(Gravity.START);
                Constants.showCustomDialog(HomeActivity.this);
                break;
            case R.id.goPlaces:
                fusedLocationClient.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (accept != null) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.com/maps/dir/" + location.getLatitude() + "," + location.getLongitude() + "/" + accept.getS_latitude() + "," + accept.getS_longitude()));
                            startActivity(intent);
                        } else if (getIntent().getSerializableExtra("TempOrder") != null) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.com/maps/dir/" + location.getLatitude() + "," + location.getLongitude() + "/" + checkStatus.getS_latitude() + "," + checkStatus.getS_longitude()));
                            startActivity(intent);
                        }
                    }

                });

                break;
            case R.id.ConfirmStartingSheet:
                if (ConfirmStartingSheet.getText().toString().equals(getResources().getString(R.string.ConfirmStarted))) {
                    providerStarted(Constants.STARTED_OROVIDER, RequestIdTemp);
                } else if (ConfirmStartingSheet.getText().toString().equals(getResources().getString(R.string.ConfirmProviderArrived))) {
                    providerConfirm(Constants.CONFIRM_OROVIDER, RequestIdTemp);
                } else if (ConfirmStartingSheet.getText().toString().equals(getResources().getString(R.string.StartTravel))) {
                    startTravel(Constants.STARTED_REQUEST, RequestIdTemp);
                } else if (ConfirmStartingSheet.getText().toString().equals(getResources().getString(R.string.FinishTravel))) {
                    endTravel(Constants.END_REQUEST, RequestIdTemp);
                }
                break;
            case R.id.CancelTripSheet:
                cancelRequest(Constants.CANCEL_REQUEST, RequestIdTemp);
                break;

            case R.id.CallUserSheet:
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(HomeActivity.this, "" + UserMobile);
                } else {
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(HomeActivity.this, "" + UserMobile);
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(HomeActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START))
            drawer.closeDrawer(Gravity.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addLineToMap = new AddLineToMap(mMap, HomeActivity.this);

        //From Accept Activity for order
        accept = (ResponseAccept.Accept) getIntent().getSerializableExtra("OrderAccept");
        checkStatus = (ResponseCheckStatus.CheckStatus) getIntent().getSerializableExtra("TempOrder");
        setData();

        try {
            Log.e("OrderAccept", accept.toString());

        } catch (Exception e) {

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        googleMap.clear();
        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(HomeActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (b) {

                getCurrentLocation();

            } else {

                Toast.makeText(HomeActivity.this, "فعل اللوكيشن", Toast.LENGTH_SHORT).show();
                showSettingAlert();
            }
        } else {

            showSettingAlert();

        }

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        Log.e("state_", "5");

        fusedLocationClient.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    if (accept != null) {

                        if (!isStepTwo) {
                            TimeTrackingOrder(false, getResources().getString(R.string.MyLocation), accept.getS_address(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                            + location.getLatitude() + "," + location.getLongitude() + "&" + Constants.DESTINATION + "="
                                            + accept.getS_latitude() + "," + accept.getS_longitude() + "&" + Constants.MODE + "="
                                            + "driving" + "&" + Constants.LANGUAGE + "="
                                            + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                            + String.valueOf(false),
                                    new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(accept.getS_latitude(), accept.getS_longitude()), HomeActivity.this);

                        }else {
                            TimeTrackingOrder(false, getResources().getString(R.string.MyLocation), accept.getD_address(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                            + location.getLatitude() + "," + location.getLongitude() + "&" + Constants.DESTINATION + "="
                                            + accept.getD_latitude() + "," + accept.getD_longitude() + "&" + Constants.MODE + "="
                                            + "driving" + "&" + Constants.LANGUAGE + "="
                                            + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                            + String.valueOf(false),
                                    new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(accept.getS_latitude(), accept.getS_longitude()), HomeActivity.this);

                        }
                    } else if (getIntent().getSerializableExtra("TempOrder") != null) {
                        if (!isStepTwo) {


                            TimeTrackingOrder(false, getResources().getString(R.string.MyLocation), checkStatus.getS_address(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                            + location.getLatitude() + "," + location.getLongitude() + "&" + Constants.DESTINATION + "="
                                            + checkStatus.getS_latitude() + "," + checkStatus.getS_longitude() + "&" + Constants.MODE + "="
                                            + "driving" + "&" + Constants.LANGUAGE + "="
                                            + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                            + String.valueOf(false),
                                    new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(checkStatus.getS_latitude(), checkStatus.getS_longitude()), HomeActivity.this);
                        }else {

                            TimeTrackingOrder(false, getResources().getString(R.string.MyLocation), checkStatus.getD_address(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                            + location.getLatitude() + "," + location.getLongitude() + "&" + Constants.DESTINATION + "="
                                            + checkStatus.getD_latitude() + "," + checkStatus.getD_longitude() + "&" + Constants.MODE + "="
                                            + "driving" + "&" + Constants.LANGUAGE + "="
                                            + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                            + String.valueOf(false),
                                    new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(checkStatus.getS_latitude(), checkStatus.getS_longitude()), HomeActivity.this);

                        }

                    } else {
                        mMap.clear();
                        LatLng latLngMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLngMyLocation).draggable(true).title(getResources().getString(R.string.MyLocation)).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
                        if (FirstTime) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngMyLocation, zoom));
                        }
                        FirstTime = false;

                    }
                }


            }

        });
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                HomeActivity.this.startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // if from activity
                ActivityCompat.finishAffinity((Activity) HomeActivity.this
                );
            }
        });


        alertDialog.show();
    }


    public void getOnlineStatus() {
        new UserAPI().getOnlineStatus(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOnLine result1 = (ResponseOnLine) result;
                if (result1.isSuccess()) {
                    if (result1.getOnline() == 1) {
                        switchAvailable.setChecked(true);
                    } else {
                        switchAvailable.setChecked(false);
                    }
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void setOnlineStatus(int NumOnline) {
        new UserAPI().setOnlineStatus(NumOnline + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOnLine result1 = (ResponseOnLine) result;


            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void TimeTrackingOrder(final boolean FromSheet, final String address,
                                  final String address2, String link, final LatLng LatLangRes, final LatLng LatLangDriver,
                                  final Context context) {

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


                        if (!FromSheet) {
                            mMap.clear();

                            addLineToMap.addMarker(LatLangRes);
                            addLineToMap.addMarker(LatLangDriver);

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));
                                    mMap.addMarker(new MarkerOptions().position(LatLangDriver).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address2, "1")))));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLangDriver, 15));


                                }
                            });
                            if (FirstTime) {
                                addLineToMap.ZoomBetween2Marker(new LatLng(LatLangRes.latitude, LatLangRes.longitude), new LatLng(LatLangDriver.latitude, LatLangDriver.longitude));

                            }
                            FirstTime = false;
                        }


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

    public void cancelRequest(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(HomeActivity.this, "Loading");
        new UserAPI().cancelServices(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCancel result1 = (ResponseCancel) result;
                if (result1.isSuccess()) {
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    intent.putExtra("message", result1.getMessage());
                    startActivity(intent);
                    finish();
                } else {
                    Constants.showDialog(HomeActivity.this, result1.getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void providerStarted(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(HomeActivity.this, "Loading");
        new UserAPI().providerStarted(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProviderStarted result1 = (ResponseProviderStarted) result;
                if (result1.isSuccess()) {
                    ConfirmStartingSheet.setText(getResources().getString(R.string.ConfirmProviderArrived));
                    Constants.showDialog(HomeActivity.this, result1.getMessage());

                } else {
                    Constants.showDialog(HomeActivity.this, result1.getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void providerConfirm(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(HomeActivity.this, "Loading");
        new UserAPI().providerConfirm(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProviderStarted result1 = (ResponseProviderStarted) result;
                if (result1.isSuccess()) {
                    CancelTripSheet.setVisibility(View.GONE);
                    ConfirmStartingSheet.setText(getResources().getString(R.string.StartTravel));
                    Constants.showDialog(HomeActivity.this, result1.getMessage());
                    isStepTwo = true;
                    getCurrentLocation();

                } else {
                    Constants.showDialog(HomeActivity.this, result1.getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void startTravel(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(HomeActivity.this, "Loading");
        new UserAPI().startTravel(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProviderStarted result1 = (ResponseProviderStarted) result;
                if (result1.isSuccess()) {
                    ConfirmStartingSheet.setText(getResources().getString(R.string.FinishTravel));
                    CancelTripSheet.setVisibility(View.GONE);
                    Constants.showDialog(HomeActivity.this, result1.getMessage());
                    isStepTwo = true;
                    getCurrentLocation();

                } else {
                    Constants.showDialog(HomeActivity.this, result1.getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    public void endTravel(final String URL, final int RequestId) {
        Constants.showSimpleProgressDialog(HomeActivity.this, "Loading");
        new UserAPI().endTravel(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProviderStarted result1 = (ResponseProviderStarted) result;
                if (result1.isSuccess()) {
                    Intent intent = new Intent(HomeActivity.this, FinishRateActivity.class);
                    intent.putExtra("RequestId", RequestId);
                    startActivity(intent);

                    finish();
                    Constants.showDialog(HomeActivity.this, result1.getMessage());

                } else {
                    Constants.showDialog(HomeActivity.this, result1.getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });

    }

    private void phoneCall(Context context, String telNum) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telNum));
            context.startActivity(callIntent);
        } else {
            Constants.showDialog((Activity) context, getResources().getString(R.string.permission));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9) {
            phoneCall(HomeActivity.this, "" + UserMobile);
        } else if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void setData() {
        if (accept != null) {
            RequestIdTemp = accept.getRequest_id();
            rest_bottom_layout.setVisibility(View.VISIBLE);
            UserNameSheet.setText(accept.getUser_name());
            PhoneNumberSheet.setText(accept.getUser_mobile());
            UserRatingSheet.setRating(accept.getUser_rating());
            UserMobile = accept.getUser_mobile();

            if (accept.getProvider_status() == 1) {
                ConfirmStartingSheet.setText(getResources().getString(R.string.ConfirmStarted));
            } else if (accept.getProvider_status() == 2) {
                ConfirmStartingSheet.setText(getResources().getString(R.string.ConfirmProviderArrived));
            } else if (accept.getProvider_status() == 3) {
                CancelTripSheet.setVisibility(View.GONE);
                ConfirmStartingSheet.setText(getResources().getString(R.string.StartTravel));
                isStepTwo = true;

            } else if (accept.getProvider_status() == 4) {
                CancelTripSheet.setVisibility(View.GONE);
                ConfirmStartingSheet.setText(getResources().getString(R.string.FinishTravel));
                isStepTwo = true;

            }
            try {
                Picasso.with(HomeActivity.this).load(accept.getUser_picture()).fit()
                        .into(imgUserSheet);
            } catch (Exception e) {

            }
        } else if (checkStatus != null) {
            rest_bottom_layout.setVisibility(View.VISIBLE);
            RequestIdTemp = checkStatus.getRequest_id();
            UserNameSheet.setText(checkStatus.getUser_name());
            PhoneNumberSheet.setText(checkStatus.getUser_mobile());
            UserRatingSheet.setRating(checkStatus.getUser_rating());
            UserMobile = checkStatus.getUser_mobile();


            if (checkStatus.getProvider_status() == 1) {
                ConfirmStartingSheet.setText(getResources().getString(R.string.ConfirmStarted));
            } else if (checkStatus.getProvider_status() == 2) {
                ConfirmStartingSheet.setText(getResources().getString(R.string.ConfirmProviderArrived));
            } else if (checkStatus.getProvider_status() == 3) {
                CancelTripSheet.setVisibility(View.GONE);
                isStepTwo = true;

                ConfirmStartingSheet.setText(getResources().getString(R.string.StartTravel));
            } else if (checkStatus.getProvider_status() == 4) {
                CancelTripSheet.setVisibility(View.GONE);
                ConfirmStartingSheet.setText(getResources().getString(R.string.FinishTravel));
                isStepTwo = true;

            }
            try {
                Picasso.with(HomeActivity.this).load(checkStatus.getUser_picture()).fit()
                        .into(imgUserSheet);
            } catch (Exception e) {

            }
        }
    }
}

