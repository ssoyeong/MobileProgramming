package gachon.mpclass.pearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

// 식물키우기 - 현재는 로그인 성공 후 화면 - 현재 해당 activity 기능: 로그아웃, 탈퇴
public class GrowingPlantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProfileActivity";

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    DatabaseReference mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userDatabaseReference = mRootDatabaseReference.child("Users");
    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private TextView textivewDelete;
    private Button buttonRest;
    private TextView textViewLevel;
    private TextView textViewScore;
    private ImageView imageViewPlant;

    private Long report;
    private Long share;
    private int score = 0;
    private User theUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growing_plant);

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textivewDelete = (TextView) findViewById(R.id.textviewDelete);
        buttonRest = (Button) findViewById(R.id.btnRest);
        textViewLevel = (TextView)findViewById(R.id.level);
        textViewScore = (TextView)findViewById(R.id.score);
        imageViewPlant = (ImageView)findViewById(R.id.plantImage);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //textViewUserEmail의 내용을 변경해 준다.
        textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail()+"으로 로그인 하였습니다.");


        buttonRest.setOnClickListener(this);


        //유저 report, share 정보 가져오기
        theUser = new User();
        String uid = user.getUid();




                // report, share 수 가져오기
        userDatabaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                report = (Long)snapshot.child("report").getValue();
                Log.d("score", "report 수: " + report);



                share = (Long)snapshot.child("share").getValue();
                Log.d("score", "share 수: " + share);
//                score = score + Long.valueOf(share).intValue() * 20;
                score = Long.valueOf(report).intValue() * 10 + Long.valueOf(share).intValue() * 20;
                Log.d("score", "score: " + score);
                textViewScore.setText(String.valueOf(score));


//                theUser.setReport(Long.valueOf(report).intValue());
//                Toast.makeText(GrowingPlantActivity.this, "theUser report 수: "+ theUser.getReport(), Toast.LENGTH_LONG).show();


//                report_num = Long.valueOf(report).intValue();
//                score = report * 10;
//                Toast.makeText(GrowingPlantActivity.this, "report 점수: "+ score, Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });






//
//
//        // report 가져오기 및 설정     todo: share도 반영
//        userDatabaseReference.child(uid).child("report").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                report = (Long)snapshot.getValue();
//                Log.d("score", "report 수: " + report);
//                score = Long.valueOf(report).intValue() * 10;
//                Log.d("score", "score: " + score);
////                textViewScore.setText(Long.valueOf(score).intValue());
//                textViewScore.setText(String.valueOf(score));
//
//                if(0<= score && score < 20){
//                    textViewLevel.setText("Lv. 1");
//                    imageViewPlant.setImageResource(R.drawable.plant_1);
//                }
//                else if(20<= score && score < 40){
//                    textViewLevel.setText("Lv. 2");
//                    imageViewPlant.setImageResource(R.drawable.plant_2);
//                }
//                else if(40<= score && score < 60){
//                    textViewLevel.setText("Lv. 3");
//                    imageViewPlant.setImageResource(R.drawable.plant_3);
//                }
//                else if(60<= score && score < 80){
//                    textViewLevel.setText("Lv. 4");
//                    imageViewPlant.setImageResource(R.drawable.plant_3);        //todo: 식물사진 추가
//                }
//                else if(80<= score && score < 100){
//                    textViewLevel.setText("Lv. 5");
//                    imageViewPlant.setImageResource(R.drawable.plant_3);
//                }
//                else if(100<= score){
//                    textViewLevel.setText("Lv. 6");
//                    imageViewPlant.setImageResource(R.drawable.plant_3);
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//            }
//        });
//
//
//
//
//
//        // share 수 가져오기
//        userDatabaseReference.child(uid).child("share").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                share = (Long)snapshot.getValue();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//            }
//        });
//        Log.d("score", "share 수: " + share);








        // 식당 추천 임시 버튼
        buttonRest = (Button) findViewById(R.id.btnRest);

        Animation anim = AnimationUtils.loadAnimation(GrowingPlantActivity.this, R.anim.transparent);
        buttonRest.startAnimation(anim);

        buttonRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrowingPlantActivity.this, SetLocationActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


    public void onClick(View view) {
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        //회원탈퇴를 클릭하면 회원정보를 삭제한다. 삭제전에 컨펌창을 하나 띄워야 겠다.
        if(view == textivewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(GrowingPlantActivity.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(GrowingPlantActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(GrowingPlantActivity.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }
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
            Intent intent=new Intent(GrowingPlantActivity.this,CheckListActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_UserProfile)
        {
            Intent intent=new Intent(GrowingPlantActivity.this,UserProfileActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            intent.putExtra("uid",uid);
            startActivity(intent);
        }
        if(id==R.id.action_store)
        {
            Intent intent=new Intent(GrowingPlantActivity.this,SetLocationActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            intent.putExtra("uid",uid);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}