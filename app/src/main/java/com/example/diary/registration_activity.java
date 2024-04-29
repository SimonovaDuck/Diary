package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class registration_activity extends Activity {

	private EditText emaildt, passworddt;
	private String email, password;
	private FirebaseAuth mAuth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		mAuth = FirebaseAuth.getInstance();

		emaildt = findViewById(R.id.rectangle_14);
		passworddt = findViewById(R.id.rectangle_13);
		Button button_reg = findViewById(R.id.registration_bt);

		button_reg.setOnClickListener(v -> {
			email = emaildt.getText().toString();
			password = passworddt.getText().toString();
			if (TextUtils.isEmpty(email)) {
				emaildt.setError("Please enter email");
			} else if (TextUtils.isEmpty(password)) {
				passworddt.setError("Please enter password");
			} else {
				registerUser(email, password);
			}
		});
	}

	private void registerUser(String email, String password) {
		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						// Регистрация успешна, переходим к следующему экрану
						Toast.makeText(registration_activity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(registration_activity.this, enteryes_activity.class);
						startActivity(intent);
					} else {
						// Регистрация не удалась
						Toast.makeText(registration_activity.this, "Ошибка при регистрации: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
	}
}
