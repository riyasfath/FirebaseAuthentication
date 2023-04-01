package com.example.loginfireb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPverification extends AppCompatActivity {

    EditText mob,sub;
    Button b1,b2;
    FirebaseAuth Fauth;
    private String varificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        mob = findViewById(R.id.mobno);
        sub = findViewById(R.id.sub);
        b1 = findViewById(R.id.otp);
        b2 = findViewById(R.id.submit);
        Fauth = FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mob.getText().toString().isEmpty() && mob.getText().toString().length() == 10) {
                    String phoneNum = "+91" + mob.getText().toString().trim();
                    requestOTP(phoneNum);

                } else {
                    mob.setError("Phone number is not valid");

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sub.getText().toString().isEmpty()) {
                    String cod = sub.getText().toString().trim();
                    verifyCode(cod);

                }
            }
        });

    }

            private void requestOTP(String phoneNum) {

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(Fauth)
                        .setPhoneNumber(phoneNum)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OTPverification.this)
                        .setCallbacks(mCallbacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);
            }
            private PhoneAuthProvider.OnVerificationStateChangedCallbacks
                    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    varificationId = s;
                }


                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    final String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        sub.setText(code);

                    }
                    verifyCode(code);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }
            };




            private void verifyCode(String code){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(varificationId,code);
                SignInWithcredentials(credential);

            }

            private void SignInWithcredentials(PhoneAuthCredential credential) {

                Fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            Intent i = new Intent(getApplicationContext(),Home.class);
                            startActivity(i);
                            finish();

                        }
                        else {
                            Toast.makeText(OTPverification.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        }








