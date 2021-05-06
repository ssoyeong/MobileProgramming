package gachon.mpclass.pearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText etConfirmPW;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            //이미 로그인 되었다면 이 액티비티를 종료함
//            finish();
//            //그리고 profile 액티비티를 연다.
//            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //추가해 줄 ProfileActivity
//        }
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.signup_email);
        editTextPassword = (EditText) findViewById(R.id.signup_password);
        etConfirmPW=(EditText)findViewById(R.id.confirmPW);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        //textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.signup_bt);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignup.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
    }
    //
//    //Firebse creating a new user 회원가입
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(etConfirmPW.getText().toString()))
        {
            Toast.makeText(SignupActivity.this,"비밀번호 불일치",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6)
        {
            Toast.makeText(SignupActivity.this,"비밀번호 최소 6자리 이상",Toast.LENGTH_SHORT).show();
            return;
        }
//        email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //가입 요청 ---이메일!
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=firebaseAuth.getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(SignupActivity.this, new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                String email = user.getEmail();
                                String uid = user.getUid();

                                HashMap<Object,String> hashMap = new HashMap<>();
                                hashMap.put("nickname",uid);
                                hashMap.put("email",email);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                Toast.makeText(SignupActivity.this,"이메일 인증 후 로그인 하세요",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                            else{
                                Toast.makeText(SignupActivity.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //에러발생
                    //textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                    Toast.makeText(SignupActivity.this, "이메일 안보내지는 듯--등록 에러!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    //button click event
    public void onClick(View view) {
        if(view == buttonSignup) {

            registerUser();
        }

        if(view == textviewSingin) {
           
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}