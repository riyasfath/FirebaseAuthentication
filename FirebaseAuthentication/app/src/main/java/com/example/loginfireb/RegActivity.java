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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity {

    EditText runame,remail,rpass1,rpass2;
    Button reg;
    FirebaseAuth  Fauth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        runame=findViewById(R.id.uname);
        remail=findViewById(R.id.regemail);
        rpass1=findViewById(R.id.regpass1);
        rpass2=findViewById(R.id.regpass2);
        reg=findViewById(R.id.reg);
        Fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


       reg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               String  username = runame.getText().toString().trim();
               String  email = remail.getText().toString().trim();
               String  password = rpass1.getText().toString().trim();



               Fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()){
                           FirebaseUser fuser = Fauth.getCurrentUser();
                           fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Toast.makeText(RegActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                                   String userId = Fauth.getCurrentUser().getUid();
//
//                                   DocumentReference documentReference = fstore.collection("user").document(userId);
//                                   Map<String,Object> user = new HashMap<>();
//                                   user.put("fullname",runame);
//                                   user.put("email",remail);
//
//
//                                   documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                       @Override
//                                       public void onSuccess(Void unused) {
//
//                                           Toast.makeText(RegActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
//
////                                           startActivity(new Intent(getApplicationContext(),Home.class));
//
//                                       }
//                                   }).addOnFailureListener(new OnFailureListener() {
//                                       @Override
//                                       public void onFailure(@NonNull Exception e) {
//                                           Toast.makeText(RegActivity.this, "Profile not created", Toast.LENGTH_SHORT).show();
//                                       }
//                                   });
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(RegActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                               }
                           });

                           userId = Fauth.getCurrentUser().getUid();

                           DocumentReference documentReference = fstore.collection("user").document(userId);
                           Map<String,Object> user = new HashMap<>();
                           user.put("fullname",username);
                           user.put("email",email);


                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {

                                   Toast.makeText(RegActivity.this, "Profile created", Toast.LENGTH_SHORT).show();

//                                           startActivity(new Intent(getApplicationContext(),Home.class));

                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(RegActivity.this, "Profile not created", Toast.LENGTH_SHORT).show();
                               }
                           });

//                           Toast.makeText(RegActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
//                           startActivity(new Intent(getApplicationContext(),Home.class));

                       }

                   }
               });

           }
       });



    }
}