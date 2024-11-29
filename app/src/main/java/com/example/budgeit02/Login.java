package com.example.budgeit;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText emailLogin;
    Button logInButton;
    TextView forgotPassword, signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotPassword = findViewById(R.id.forgotPassword);
        mAuth = FirebaseAuth.getInstance();
        emailLogin = findViewById(R.id.emailLogin);
        logInButton = findViewById(R.id.logInButton);
        signUpText = findViewById(R.id.signUpText);

        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, ForgetPassword.class));
        });
        signUpText.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });


        // Enable Edge-to-Edge mode if needed
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditText and ImageView for password toggle
        EditText emailPassword = findViewById(R.id.emailPassword);
        ImageView togglePassword = findViewById(R.id.togglePassword);

        // Set up click listener for the password toggle
        togglePassword.setOnClickListener(view -> {
            if (emailPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // Show password
                emailPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.visibility_on); // Update icon
            } else {
                // Hide password
                emailPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.visibility_off); // Update icon
            }
            // Keep cursor at the end
            emailPassword.setSelection(emailPassword.getText().length());
        });

        logInButton.setOnClickListener(view -> {
            performLogin(emailLogin.getText().toString(), emailPassword.getText().toString());
        });
    }
    private void performLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(Login.this, MainActivity.class));
                Toast.makeText(Login.this, "Login Successful",
                        Toast.LENGTH_SHORT).show();
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthInvalidUserException e) {
                    Toast.makeText(Login.this, "Invalid Email Address",
                            Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    Toast.makeText(Login.this, "Invalid Password",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Login.this, "An Error Occured",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}