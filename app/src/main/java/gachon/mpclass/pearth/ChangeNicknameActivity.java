package gachon.mpclass.pearth;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Iterator;

public class ChangeNicknameActivity extends Activity {

    EditText nickname;
    Button check_duplicate;
    TextView available;
    Button change;
    String uid;
    Button back;
    DatabaseReference mRootDatabaseReference= FirebaseDatabase.getInstance().getReference();
    DatabaseReference userDatabaseReference=mRootDatabaseReference.child("Users");
    String new_nickname="";
    int checked=0;
    int duplicated=0; //1이면 중복->사용 불가능
    String child_nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_nickname);

        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        Log.v("받아온 uid:",uid);
        nickname=(EditText)findViewById(R.id.nickname);
        available=(TextView)findViewById(R.id.available); //사용 가능 불가능 텍스트 뷰

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            }
        });
        check_duplicate=(Button)findViewById(R.id.check_duplicate_btn); //중복체크하는 버튼
        check_duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_nickname=nickname.getText().toString();
                if(new_nickname.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"닉네임을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.v("팝업창 new nickname:",new_nickname);
                    mRootDatabaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterator<DataSnapshot> child=snapshot.getChildren().iterator();
                            while(child.hasNext())
                            {
                                child_nickname=String.valueOf(child.next().child("nickname").getValue());
                                Log.v("child.next().getvalue: ",child_nickname);
                                Log.v("<<<<<팝업창 new nickname:>",new_nickname);
                                if(new_nickname.equals(child_nickname))
                                {
                                    duplicated=1;
                                    break;
                                }
                            }
                            if(duplicated==0)
                            {
                                Log.v("***********사용 가능!!!!!!","**************");
                                available.setText("사용 가능한 닉네임입니다.");
                                checked = 1;
                                Log.d("사용 가능 닉네임: ",new_nickname);
                                Toast.makeText(getApplicationContext(),"사용 가능 아이디",Toast.LENGTH_SHORT).show();
                            }
                            else if(duplicated==1)
                            {
                                Log.v("사용 불가능!!!!!!","닉네임 중복!!!!!!");
                                available.setText("사용 불가능한 닉네임입니다. 새로운 닉네임을 입력해주세요.");
                                checked=0;
                                duplicated=0;
                                Toast.makeText(getApplicationContext(),"존재하는 아이디",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        change=(Button)findViewById(R.id.change_btn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked==0)
                {
                    Toast.makeText(getApplicationContext(),"닉네임 중복 확인을 하세요.",Toast.LENGTH_SHORT);
                    return;
                }
                else
                {

                    Log.d("UID: ",uid);
                    Log.d("변경할 닉네임: ",new_nickname);
                    userDatabaseReference.child(uid).child("nickname").setValue(new_nickname);
                    Intent intent1=new Intent();
                    Log.d("new nickname",new_nickname);
                    intent1.putExtra("new nickname",new_nickname);
                    setResult(RESULT_OK,intent1);
                    finish();
                }

            }
        });





    }
}