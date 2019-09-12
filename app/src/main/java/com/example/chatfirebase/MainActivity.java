package com.example.chatfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    String email, password;
    EditText emailEditText, passwordEditText;
    Button registor, login;
    TextView chatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatter=findViewById(R.id.chatter);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registor = findViewById(R.id.registor);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_animation);
        chatter.startAnimation(animation);
    }

    public void RegisterMethod(View view) {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {


                    if (task.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(),chatscreen.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,
                                auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        //finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Couldn't register, try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void login(View view) {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(),chatscreen.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "welcome" + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "something wentwrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

