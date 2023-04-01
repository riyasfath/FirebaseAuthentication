package com.example.loginfireb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText lemail,lpass;
    Button log,signup;
    FirebaseAuth Fauth;
    TextView fgttxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lemail=findViewById(R.id.logemail);
        lpass=findViewById(R.id.logpass);
        log=findViewById(R.id.login);
        signup=findViewById(R.id.signintnt);
        Fauth = FirebaseAuth.getInstance();
        fgttxt = findViewById(R.id.frgttxt);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = lemail.getText().toString().trim();
                String password = lpass.getText().toString().trim();

                Fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                    }
                });

            }
        });


    }

    public void OnClick(View view) {

        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),ForgotPassword.class));



    }
}