package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class zad_activity extends Activity {

	public ImageView stat;

	public ImageView create_new_text;
	private List<View> taskBlocks = new ArrayList<>();
	private String tasksId;

	private static final int REQUEST_CODE_NEW_NOTE = 1;
	private FirebaseFirestore db; // Объект для работы с базой данных Cloud Firestore


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.zad);
		taskBlocks = new ArrayList<>();



		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);

		db = FirebaseFirestore.getInstance(); // Получение экземпляра Cloud Firestore
		// Получение текущего пользователя и загрузка его заметок
		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			String currentUserId = currentUser.getUid();
			loadUserTasks(currentUserId); // Загрузка заметок текущего пользователя
		}

	}
	private void loadUserTasks(String userId) {
		// Получение записей определенного пользователя из базы данных Cloud Firestore
		db.collection("tasks")
				.whereEqualTo("userId", userId) // Фильтр по полю "userId"
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							for (QueryDocumentSnapshot document : task.getResult()) {
								String taskId = document.getString("taskId");
								tasksId = document.getId();
								Timestamp timestamp = (Timestamp) document.get("Date");
								Date date = timestamp.toDate();
								addNewTaskBlock(tasksId,taskId,date); // Добавление каждой записи на экран с указанием идентификатора
							}
						}
					}
				});
	}
	private void addNewTaskBlock(String tasksId,String taskId,Date Date) {
		// Получаем title из коллекции bank_ex по taskId
		db.collection("bank_ex").document(taskId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(Task<DocumentSnapshot> task) {
				if (task.isSuccessful()) {
					DocumentSnapshot document = task.getResult();
					if (document.exists()) {
						// Получаем title из документа
						String taskTitle = document.getString("text");
						
						LayoutInflater inflater = LayoutInflater.from(zad_activity.this);
						View task_lay = inflater.inflate(R.layout.task_layout, null);

						// Найдем TextView в карточке и установим заголовок
						TextView titleTextView = task_lay.findViewById(R.id.title_of_zad);
						titleTextView.setText(taskTitle);

						// Найдем TextView для noteId и установим значение
						TextView idTextView = task_lay.findViewById(R.id.zad_id);
						idTextView.setText(tasksId);

						TextView dateTextView = task_lay.findViewById(R.id.date);
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()); // Формат даты
						dateTextView.setText(sdf.format(Date));

						// Добавляем карточку в контейнер в меню
						LinearLayout menuLayout = findViewById(R.id.tasks_layout);
						menuLayout.addView(task_lay);

						// Сохраняем созданную карточку для возможности последующего обращения к ней
						taskBlocks.add(task_lay);
					}
				}
			}
		});
	}

	public void onClickRealNote(View view) {

		View task_lay = (View) view.getParent();
		if (task_lay == null) {
			Log.e("zad_activity", "task_lay is null");
			return;
		}

		TextView titleTextView = task_lay.findViewById(R.id.title_of_zad);
		if (titleTextView == null) {
			Log.e("zad_activity", "titleTextView is null");
			return;
		}
		String zadTitle = titleTextView.getText().toString();

		TextView idTextView = task_lay.findViewById(R.id.zad_id);
		if (idTextView == null) {
			Log.e("zad_activity", "idTextView is null");
			return;
		}
		String zadId = idTextView.getText().toString();

		// Создаем новый Intent
		Intent intent = new Intent(this, example_activity.class);
		// Передаем заголовок и идентификатор записи в Intent
		intent.putExtra("zad_id", zadId);
		intent.putExtra("zad_title", zadTitle);
		// Запускаем новую активити с переданными данными о записи

		startActivity(intent);
	}


	public void onClickNotes(View view){
		Intent intent = new Intent (this, menu_activity.class);
		startActivity(intent);
	}
	public void onClickZads(View view){
		Intent intent = new Intent (this,zad_activity.class);
		startActivity(intent);
	}
	public void onClickWonts(View view){
		Intent intent = new Intent (this,wonts_activity.class);
		startActivity(intent);
	}

	public void onClickNewNote(View view){
		Intent intent = new Intent (this,note_activity.class);
		startActivity(intent);
	}

	public void onClickStatistic(View view){
		Intent intent = new Intent (this,emotions_activity.class);
		startActivity(intent);
	}

}
	
	