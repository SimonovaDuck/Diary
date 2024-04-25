package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



public class registration_activity extends Activity {

	private View _bg___registration;
	private TextView reg;
	private TextView quest;
	public TextView ent;
	private EditText logindt, emaildt, passworddt;
	private String login, email, password;
	private FirebaseFirestore db;
	private Button button_reg;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		db = FirebaseFirestore.getInstance();

		_bg___registration = findViewById(R.id._bg___registration);
		reg = findViewById(R.id.reg);
		quest = findViewById(R.id.quest);
		passworddt = findViewById(R.id.rectangle_13);
		emaildt = findViewById(R.id.rectangle_14);
		logindt = findViewById(R.id.rectangle_12);
		ent = findViewById(R.id.ent);
		button_reg = findViewById(R.id.registration_bt);

		button_reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login = logindt.getText().toString();
				email = emaildt.getText().toString();
				password = passworddt.getText().toString();
				if (TextUtils.isEmpty(login)) {
					logindt.setError("Пожалуйста введите имя");
				} else if (TextUtils.isEmpty(email)) {
					emaildt.setError("Пожалуйста введите email");
				} else if (TextUtils.isEmpty(password)) {
					passworddt.setError("Пожалуйста введите пароль");
				} else {
					addDataToFirestore(login,email, password);
				}
			}

		});

	}


	public void onClickEnt(View view) {
		Intent intent = new Intent(this, enteryes_activity.class);
		startActivity(intent);
	}



	private void addDataToFirestore(String login, String email, String password) {
		CollectionReference dbusers = db.collection("users");

		users user = new users(login, email, password);
		dbusers.whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
			@Override
			public void onSuccess(QuerySnapshot querySnapshot) {
				if (!querySnapshot.isEmpty()) {
					Toast.makeText(registration_activity.this, "Аккаунт с этим email уже существует", Toast.LENGTH_SHORT).show();
				} else {
					Log.d("RegistrationActivity", "Email is unique. Adding user to Firestore.");
					dbusers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
						@Override
						public void onSuccess(DocumentReference documentReference) {
							Toast.makeText(registration_activity.this, "Аккаунт был добавлен в базу данных", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(registration_activity.this, enteryes_activity.class);
							startActivity(intent);
						}
					}).addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {
							Toast.makeText(registration_activity.this, "Ошибка при добавлении аккаунта: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				Toast.makeText(registration_activity.this, "Ошибка при проверке существования аккаунта: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.e("RegistrationActivity", "Failed to check existing account", e);
			}
		});

	}
}

