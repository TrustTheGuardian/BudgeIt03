package com.example.budgeit02;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText signLogIn, signPassword;
    Button createButton;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        signLogIn = findViewById(R.id.signLogIn);
        mAuth = FirebaseAuth.getInstance();
        signPassword = findViewById(R.id.signPassword);
        createButton = findViewById(R.id.createButton);
        loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, Login.class));
        });
        createButton.setOnClickListener(view -> {
            createAccount(signLogIn.getText().toString(), signPassword.getText().toString());

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView togglePassword = findViewById(R.id.togglePassword);

        // Set up click listener for the password toggle
        togglePassword.setOnClickListener(view -> {
            if (signPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // Show password
                signPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.visibility_on); // Update icon
            } else {
                // Hide password
                signPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.visibility_off); // Update icon
            }
            // Keep cursor at the end
            signPassword.setSelection(signPassword.getText().length());
        });
    }
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(Register.this, Login.class));
                Toast.makeText(Register.this, "Register successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                try{
                    throw task.getException();
                }catch(FirebaseAuthUserCollisionException e){
                    Toast.makeText(Register.this, "Email has already been taken",
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(Register.this, "An Error Occurred!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}