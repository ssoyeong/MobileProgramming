package gachon.mpclass.pearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    //define view objects
    EditText editTextEmail; //이메일
    EditText editTextPassword; //비밀번호
    Button buttonSignin; //로그인 버튼
    TextView textviewSingin;
    TextView textviewMessage;
    TextView textviewFindPassword; //비밀번호 찾기
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            //이미 로그인 되었다면 이 액티비티를 종료함
//            finish();
//            //그리고 profile 액티비티를 연다.
//            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //추가해 줄 ProfileActivity
//        }
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        //textviewSingin= (TextView) findViewById(R.id.register);
        //textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        textviewFindPassword = (TextView) findViewById(R.id.find_pw);
        buttonSignin = (Button) findViewById(R.id.login_btn);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignin.setOnClickListener((View.OnClickListener) this);
        textviewSingin.setOnClickListener((View.OnClickListener) this);
        textviewFindPassword.setOnClickListener((View.OnClickListener) this);
    }

    //firebase userLogin method
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();


        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        if(task.isSuccessful()) {
                            if(!user.isEmailVerified())
                            {
                                Toast.makeText(getApplicationContext(), "이메일 인증 확인 필요!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                finish();
                                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                            //textviewMessage.setText("로그인 실패 유형\n - password가 맞지 않습니다.\n -서버에러");
                        }
                    }
                });
    }




    public void onClick(View view) {
        if(view == buttonSignin) {
            userLogin();
        }
        if(view == textviewSingin) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, FindpwActivity.class));
        }
    }
}