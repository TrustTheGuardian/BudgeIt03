package com.example.budgeit02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText forgotEmail;
    Button resetButton;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        forgotEmail = findViewById(R.id.forgotEmail);
        mAuth = FirebaseAuth.getInstance();
        resetButton = findViewById(R.id.resetButton);
        loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(view -> {
            startActivity(new Intent(ForgetPassword.this, Login.class));
        });

        resetButton.setOnClickListener(view ->{
            mAuth.sendPasswordResetEmail(forgotEmail.getText().toString()).addOnCompleteListener(task ->{
                startActivity(new Intent(ForgetPassword.this, Login.class));
                Toast.makeText(ForgetPassword.this,
                        task.isSuccessful()?"Password reset link has been sent":"An error occured",
                        Toast.LENGTH_SHORT).show();
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}