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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class note_activity extends Activity {

	private TextView note_cic_new;
	private TextView note_cic_author;

	private FirebaseFirestore db;

	private View _bg__note;

	private View foot;



	private View rectangle_5;

	private View rectangle_6;
	;
	private View rectangle_7;

	private View rectangle_1;
	private TextView diary;
	private TextView new_zadanie;

	private EditText name_of_text;

	private EditText user_input_text;





	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);

		// Инициализируйте экземпляр FirebaseFirestore
		db = FirebaseFirestore.getInstance();

		// Найдите TextView для цитаты и автора
		note_cic_new = findViewById(R.id.note_cic_new);
		note_cic_author = findViewById(R.id.note_cic_author);
		new_zadanie = findViewById(R.id.new_zadanie);
		// Получите случайную цитату и автора из базы данных
		getRandomQuote();
		getRandomTask();


	}
	private void getRandomQuote() {
		// Получите ссылку на коллекцию цитат в вашей базе данных
		CollectionReference quotesRef = db.collection("quote");

		// Получите все документы в коллекции цитат
		quotesRef.get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				// Получите список всех документов
				QuerySnapshot querySnapshot = task.getResult();
				if (querySnapshot != null && !querySnapshot.isEmpty()) {
					// Случайным образом выбрать один документ из списка
					int randomIndex = new Random().nextInt(querySnapshot.size());
					DocumentSnapshot document = querySnapshot.getDocuments().get(randomIndex);

					// Получить значение поля цитаты и автора из выбранного документа
					String quote = document.getString("text");
					String author = document.getString("author");

					// Обновите текст в TextView для цитаты и автора
					note_cic_new.setText(quote);
					note_cic_author.setText(author);
				}
			}
		});
	}

	private void getRandomTask() {
		// Получите ссылку на коллекцию заданий в вашей базе данных
		CollectionReference tasksRef = db.collection("bank_ex");

		// Получите все документы в коллекции заданий
		tasksRef.get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				// Получите список всех документов
				QuerySnapshot querySnapshot = task.getResult();
				if (querySnapshot != null && !querySnapshot.isEmpty()) {
					// Случайным образом выбрать один документ из списка
					int randomIndex = new Random().nextInt(querySnapshot.size());
					DocumentSnapshot document = querySnapshot.getDocuments().get(randomIndex);

					// Получить значение поля задания из выбранного документа
					String taskText = document.getString("text");

					// Обновите текст в TextView для нового задания
					new_zadanie.setText(taskText);
				}
			}
		});
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
	//save доделать
	public void onClickNewNote(View view){
		// Получаем текст из полей ввода
		name_of_text = findViewById(R.id.name_of_text);
		user_input_text = findViewById(R.id.user_input_text);
		String title = name_of_text.getText().toString();
		String content = user_input_text.getText().toString();

		// Проверяем, что поля не пустые
		if (TextUtils.isEmpty(title)) {
			name_of_text.setError("Введите заголовок");
			return;
		}
		if (TextUtils.isEmpty(content)) {
			user_input_text.setError("Введите содержание");
			return;
		}

		// Получаем текущего пользователя
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser != null) {
			// Получаем идентификатор текущего пользователя
			String userId = currentUser.getUid();

			// Создаем новую заметку
			Map<String, Object> note = new HashMap<>();
			note.put("title", title);
			note.put("content", content);
			note.put("userId", userId); // Добавляем идентификатор пользователя

			// Добавляем заметку в базу данных
			db.collection("notes")
					.add(note)
					.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
						@Override
						public void onSuccess(DocumentReference documentReference) {
							String noteId = documentReference.getId(); // Получаем айдишник добавленной заметки
							// Успешно добавлено
							Toast.makeText(note_activity.this, "Заметка успешно создана", Toast.LENGTH_SHORT).show();
							// Очищаем поля ввода после добавления записи
							name_of_text.setText("");
							user_input_text.setText("");
						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {
							// Ошибка при добавлении
							Toast.makeText(note_activity.this, "Ошибка при создании заметки: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
		} else {
			// Пользователь не аутентифицирован, обработайте этот случай по вашему усмотрению
			Toast.makeText(note_activity.this, "Пользователь не аутентифицирован", Toast.LENGTH_SHORT).show();
		}
	}

// delete доделать
//	public void onClickStatistic(View view){
//		Intent intent = new Intent (this,emotions_activity.class);
//		startActivity(intent);
//	}

}