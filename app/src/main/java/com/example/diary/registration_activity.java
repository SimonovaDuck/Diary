package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
	private View rectangle_12;
	private View rectangle_13;
	private View rectangle_14;
	private TextView reg;
	private TextView quest;
	public TextView ent;
	private EditText usernameedt, emaildt, passwordedt;
	private  String username, email, password;
	private  FirebaseFirestore db;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		db = FirebaseFirestore.getInstance();

		_bg___registration = findViewById(R.id._bg___registration);
		rectangle_12 = findViewById(R.id.rectangle_12);
		rectangle_13 = findViewById(R.id.rectangle_13);
		rectangle_14 = findViewById(R.id.rectangle_14);
		reg = findViewById(R.id.reg);
		quest = findViewById(R.id.quest);
		EditText passwordedt = findViewById(R.id.rectangle_13);
		EditText emaildt = findViewById(R.id.rectangle_14);
		EditText usernameedt = findViewById(R.id.rectangle_12);
		ent = findViewById(R.id.ent);



	}

	public void onClickEnt(View view){
		Intent intent = new Intent (this,enteryes_activity.class);
		startActivity(intent);
	}

	public void onClickregistration(View view){

		username = usernameedt.getText().toString();
		email = emaildt.getText().toString();
		password = passwordedt.getText().toString();
		if (TextUtils.isEmpty(username)) {
			usernameedt.setError("Пожалуйста введите имя");
		} else if (TextUtils.isEmpty(email)) {
			emaildt.setError("Пожалуйста введите email");
		} else if (TextUtils.isEmpty(password)) {
			passwordedt.setError("Пожалуйста введите пароль");
		} else {
			addDataToFirestore(username, email, password);
		}


//		Intent intent = new Intent (this,enteryes_activity.class);
//		startActivity(intent);
	}
	private void addDataToFirestore(String username, String email, String password){
		CollectionReference dbusers = db.collection("users");

		users users = new users(username, email,password);
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
