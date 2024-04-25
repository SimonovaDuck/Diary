package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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



public class registration_activity extends Activity {

	private View _bg___registration;
	private TextView reg;
	private TextView quest;
	public TextView ent;
	private EditText usernamedt, emaildt, passworddt;
	private String username, email, password;
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
		usernamedt = findViewById(R.id.rectangle_12);
		ent = findViewById(R.id.ent);
		button_reg = findViewById(R.id.registration_bt);

		button_reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				username = usernamedt.getText().toString();
				email = emaildt.getText().toString();
				password = passworddt.getText().toString();
				if (TextUtils.isEmpty(username)) {
					usernamedt.setError("Пожалуйста введите имя");
				} else if (TextUtils.isEmpty(email)) {
					emaildt.setError("Пожалуйста введите email");
				} else if (TextUtils.isEmpty(password)) {
					passworddt.setError("Пожалуйста введите пароль");
				} else {
					addDataToFirestore(username, email, password);
					Intent intent = new Intent (v.getContext(),enteryes_activity.class);
					startActivity(intent);
				}
			}

		});

	}


	public void onClickEnt(View view) {
		Intent intent = new Intent(this, enteryes_activity.class);
		startActivity(intent);
	}



	private void addDataToFirestore(String username, String email, String password) {
		CollectionReference dbusers = db.collection("users");

		users users = new users(username, email, password);
		dbusers.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
			@Override
			public void onSuccess(DocumentReference documentReference) {
				Toast.makeText(registration_activity.this, "Аккаунт был добавлен в базу данных", Toast.LENGTH_SHORT).show();
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				Toast.makeText(registration_activity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
			}
		});
	}
}

