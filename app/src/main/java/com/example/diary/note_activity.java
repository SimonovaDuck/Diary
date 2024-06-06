package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import java.util.ArrayList;


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

	private TextView new_zadanie;

	private EditText name_of_text;

	private EditText user_input_text;

	private String taskId, quoteId, moodsId, tasksId; // Поле для хранения айди задания
	private ArrayList<String> selectedMoodIds = new ArrayList<>();

	private Map<String, Object> note = new HashMap<>(); // Переменная для хранения заметки
	private String noteId;





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
					quoteId = document.getId();
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
					taskId = document.getId();
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
	public void onClickNewNote(View view) {
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
			note.put("quoteId", quoteId); // Добавляем айдишник цитаты
			note.put("taskId", taskId); // Добавляем айдишник задания
			note.put("Date", new java.util.Date());


			// Добавляем новые поля в заметку
			note.put("tasks_Id", ""); // Пустое значение по умолчанию
			note.put("moods_Id", ""); // Пустое значение по умолчанию

			// Добавляем заметку в базу данных
			db.collection("notes")
					.add(note)
					.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
						@Override
						public void onSuccess(DocumentReference documentReference) {
							// Успешно добавлено
							Toast.makeText(note_activity.this, "Заметка успешно создана", Toast.LENGTH_SHORT).show();
							// Очищаем поля ввода после добавления записи
							name_of_text.setText("");
							user_input_text.setText("");
							noteId = documentReference.getId(); // Установите значение noteId
							// Создаем новую запись в коллекции "tasks"
							createNewTask(taskId, userId);
							// Сохраняем настроение
							saveMood(userId);

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
		Intent intent = new Intent (this, menu_activity.class);
		startActivity(intent);
	}
	private void createNewTask(String taskId, String userId) {
		// Создаем новую запись в коллекции "tasks"
		Map<String, Object> task = new HashMap<>();
		task.put("taskId", taskId); // Добавляем айдишник записи из "notes"
		task.put("userId", userId); // Добавляем идентификатор пользователя
		task.put("content", "");
		task.put("Date", new java.util.Date());



		// Добавляем задачу в коллекцию "tasks"
		db.collection("tasks")
				.add(task)
				.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
					@Override
					public void onSuccess(DocumentReference documentReference) {
						tasksId = documentReference.getId(); // Присваиваем значение tasksId
						// Обновляем значение tasksId в заметке
						updateNoteWithTasksId(tasksId);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						// Ошибка при добавлении
						Toast.makeText(note_activity.this, "Ошибка при создании задачи: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
	}

	public void onClickEmotion(View view) {
		ImageView imageView = (ImageView) view;
		int viewId = view.getId();
		boolean isPressed = false; // Флаг для отслеживания состояния изображения

		// Проверяем текущее состояние изображения
		if (imageView.getTag() != null) {
			isPressed = (boolean) imageView.getTag();
		}

		// Проверяем ID нажатого изображения и изменяем изображение в соответствии с флагом isPressed
		if (viewId == R.id.happy) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundhappy);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("happy"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.happy);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("happy"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.nice) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundnice);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("nice"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.nice);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("nice"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.love) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundlove);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("love"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.love);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("love"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.bue) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundbue);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("bue"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.bue);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("bue"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.hmm) {

			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundhmm);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("hmm"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.hmm);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("hmm"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.angry) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundangry);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("angry"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.angry);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("angry"); // Удаляем id настроения из списка
			}
		} else if (viewId == R.id.cry) {
			if (!isPressed) {
				imageView.setImageResource(R.drawable.roundcry);
				imageView.setTag(true); // Устанавливаем флаг нажатого состояния
				selectedMoodIds.add("cry"); // Добавляем id настроения в список
			} else {
				imageView.setImageResource(R.drawable.cry);
				imageView.setTag(false); // Сбрасываем флаг нажатого состояния
				selectedMoodIds.remove("cry"); // Удаляем id настроения из списка
			}
		}
	}

	private void saveMood(String userId) {
		// Создаем новую запись в коллекции "moods"
		Map<String, Object> mood = new HashMap<>();
		mood.put("userId", userId);
		mood.put("moodId", new ArrayList<>(selectedMoodIds));
		mood.put("Date", new java.util.Date());

		// Добавляем настроение в коллекцию "moods"
		db.collection("moods")
				.add(mood)
				.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
					@Override
					public void onSuccess(DocumentReference documentReference) {
						moodsId = documentReference.getId(); // Присваиваем значение moodsId
						// Обновляем значение tasksId в заметке
						updateNoteWithMoodsId(moodsId);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						// Ошибка при добавлении
						Toast.makeText(note_activity.this, "Ошибка при сохранении настроения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
		selectedMoodIds.clear();
	}
	private void updateNoteWithTasksId(String tasksId) {
		// Обновляем значение tasksId в заметке
		Map<String, Object> updates = new HashMap<>();
		updates.put("tasks_Id", tasksId);

		// Обновляем заметку в базе данных с новым значением tasksId
		db.collection("notes")
				.document(noteId)
				.update(updates);
	}
	private void updateNoteWithMoodsId(String moodsId) {
		// Обновляем значение moodsId в заметке
		Map<String, Object> updates = new HashMap<>();
		updates.put("moods_Id", moodsId);

		// Обновляем заметку в базе данных с новым значением moodsId
		db.collection("notes")
				.document(noteId)
				.update(updates);
	}


	// delete доделать
	public void onClickDelete(View view){
		Intent intent = new Intent (this, menu_activity.class);
		startActivity(intent);
	}

}