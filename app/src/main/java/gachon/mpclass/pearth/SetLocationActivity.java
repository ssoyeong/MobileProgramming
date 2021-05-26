package gachon.mpclass.pearth;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SetLocationActivity extends AppCompatActivity {

    GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    String area, areaTown;
    String SIGUN = "";
    String SIDO = "";
    TextView textView;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    Button complete_btn;
    Button gps_btn;

    private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("<<>>>><<>>>","SetLocationActivity");
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        setContentView(R.layout.activity_set_location);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }

        getSupportActionBar().setTitle("추천 비건 음식점");
        Toolbar toolbar = findViewById(R.id.my_toolbar);


        gps_btn = (Button) findViewById(R.id.gps_btn);
        complete_btn = (Button) findViewById(R.id.complete_btn);
        final Spinner spin1 = (Spinner)findViewById(R.id.spinnerArea);
        final Spinner spin2 = (Spinner)findViewById(R.id.spinnerArea2);

        //adspin1 = ArrayAdapter.createFromResource(this, R.array.spinner_do, R.layout.spinneritem);
        adspin1 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do, R.layout.spinneritem);
        // adspin1.setDropDownViewResource(R.layout.spinneritem);
        adspin1.setDropDownViewResource(R.layout.spinneritem);
        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area=adspin1.getItem(position).toString();
                if (adspin1.getItem(position).equals("서울특별시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Seoul, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            areaTown=adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if (adspin1.getItem(position).equals("부산광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Busan, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if (adspin1.getItem(position).equals("대구광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Daegu, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            areaTown=adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if (adspin1.getItem(position).equals("인천광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Gwangju, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("광주광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Incheon, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("대전광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Daejeon, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("울산광역시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Ulsan, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("세종특별자치시")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Sejong, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("경기도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Gyeonggi, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("강원도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Gangwon, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("충청북도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Chungbuk,R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("충청남도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Chungnam, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("전라북도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Jeonbuk, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                else if (adspin1.getItem(position).equals("전라남도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Jeonnam, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                else if (adspin1.getItem(position).equals("경상북도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Gyeongbuk, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                else if (adspin1.getItem(position).equals("경상남도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Gyeongnam, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                else if (adspin1.getItem(position).equals("제주특별자치도")){
                    adspin2 = ArrayAdapter.createFromResource(SetLocationActivity.this, R.array.spinner_do_Jeju, R.layout.spinneritem);
                    adspin2.setDropDownViewResource(R.layout.spinneritem);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areaTown=adspin2.getItem(position).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetLocationActivity.this, MapActivity.class);
                Bundle mybundle = new Bundle();

                String[] areas = area.split(" ");
                if(areas.length == 2){
                    SIGUN = area.split(" ")[0];
                    SIDO = area.split(" ")[1] + " " + areaTown;
                }else {
                    SIGUN = areas[0];
                    SIDO = areaTown;
                }

                mybundle.putString("SIGUN", SIGUN);
                mybundle.putString("SIDO", SIDO);
                mybundle.putString("Case", "List");


                Toast.makeText(SetLocationActivity.this, SIGUN + " " + SIDO, Toast.LENGTH_LONG).show();



                intent.putExtras(mybundle);
                startActivity(intent);
                finish();


            }
        });

        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gpsTracker = new GpsTracker(SetLocationActivity.this);
                Intent intent = new Intent(SetLocationActivity.this, MapActivity.class);
                Bundle mybundle = new Bundle();

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);

                Log.d("address", address);
                String split[] = address.split(" ");
                area = split[2];
                int isShort = 0;
                if(!split[3].substring(split[3].length()-1).equals("동")) {
                    area = area + " " + split[3];

                    if(split.length > 4) {
                        if (split[4].substring(split[4].length() - 1).equals("동")) {
                            areaTown = split[4];
                        }
                    }
                    else isShort = 1;
                }
                else {
                    areaTown = split[3];
                }

                String[] areas = area.split(" ");
                if(areas.length == 2){
                    SIGUN = area.split(" ")[0];
                    if(isShort == 0) {
                        SIDO = area.split(" ")[1] + " " + areaTown;
                    }
                    else{
                        SIDO = area.split(" ")[1];
                    }
                }else {
                    SIGUN = areas[0];
                    SIDO = areaTown;
                }

                mybundle.putString("SIGUN", SIGUN);
                mybundle.putString("SIDO", SIDO);
                mybundle.putString("Case", "GPS");

                Toast.makeText(SetLocationActivity.this, SIGUN + " " + SIDO, Toast.LENGTH_LONG).show();

                intent.putExtras(mybundle);
                startActivity(intent);
                finish();


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(SetLocationActivity.this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SetLocationActivity.this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(SetLocationActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(SetLocationActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(SetLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(SetLocationActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(SetLocationActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(SetLocationActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(SetLocationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(SetLocationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(SetLocationActivity.this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(SetLocationActivity.this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(SetLocationActivity.this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(SetLocationActivity.this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetLocationActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }




    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션바 숨기기
    private void hideActionBar () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_record) {
            Intent homeIntent = new Intent(this, RecordActivity.class);
            startActivity(homeIntent);
        }
        if (id == R.id.action_analysis) {
            Intent settingIntent = new Intent(this, Analysis.class);
            startActivity(settingIntent);
        }
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(this, Shareboard.class);
            startActivity(shareIntent);
        }
        if (id == R.id.action_plant) {
            Intent plantIntent = new Intent(this, GrowingPlantActivity.class);
            startActivity(plantIntent);
        }
        if(id==R.id.action_checklist)
        {
            Intent intent=new Intent(this,CheckListActivity.class);
            String uid = user.getUid();
            Log.v("uid: ",uid);
          intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_UserProfile)
        {
            Intent intent=new Intent(this,UserProfileActivity.class);
            String uid = user.getUid();
            Log.v("uid: ",uid);
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_post){
            Intent PostIntent = new Intent(this, Userpost.class);
            startActivity(PostIntent);
        }
        if(id==R.id.action_store) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_back) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}


