package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class example_activity extends Activity {


	public ImageView stat;

	public ImageView create_new_text;

	private EditText  user_input;
	private TextView name_of_zad_text;
	private FirebaseFirestore db;
	private String zadId,zadTitle;
	private TextView dateTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example);


		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);
		name_of_zad_text = (TextView) findViewById(R.id.name_of_zad_text);
		user_input = (EditText) findViewById(R.id.user_input);
		dateTextView = findViewById(R.id.date);



		// Получаем айдишник записи из Intent
		Intent intent = getIntent();
		if (intent != null) {
			zadId = intent.getStringExtra("zad_id");
			zadTitle = intent.getStringExtra("zad_title");
			Log.d("ExampleActivity", "zadId: " + zadId);
			Log.d("ExampleActivity", "zadTitle: " + zadTitle);
			if (zadId != null && zadTitle != null) { // Добавьте проверку zadTitle != null
				// Инициализируем Firebase
				db = FirebaseFirestore.getInstance();
				// Получаем документ записи из базы данных
				DocumentReference noteRef = db.collection("tasks").document(zadId);
				noteRef.get().addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						DocumentSnapshot document = task.getResult();
						if (document.exists()) {
							// Получаем контент записи из документа
							String zadContent = document.getString("content");
							// Устанавливаем заголовок и контент записи в соответствующие элементы интерфейса
							name_of_zad_text.setText(zadTitle);
							user_input.setText(zadContent);
							Timestamp timestamp = (Timestamp) document.get("Date");
							Date date = timestamp.toDate();
							SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()); // Формат даты
							dateTextView.setText(sdf.format(date));
						}
					}
				});
			}
		}

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
	// Получаем новые значения текста и контента из элементов интерфейса
	String newContent = user_input.getText().toString();

	// Обновляем документ в базе данных
	updateNoteInDatabase(newContent);
}
	private void updateNoteInDatabase(String newContent) {
		// Проверяем, что у нас есть идентификатор записи
		if (zadId != null) {
			// Получаем ссылку на документ записи в коллекции "notes"
			DocumentReference noteRef = db.collection("tasks").document(zadId);

			// Обновляем поля "content" в документе
			noteRef.update( "content", newContent)
					.addOnSuccessListener(aVoid -> {
						// Успешное обновление данных
						Toast.makeText(this, "Текст успешно обновлен", Toast.LENGTH_SHORT).show();
					})
					.addOnFailureListener(e -> {
						// Обработка ошибок при обновлении данных
						Toast.makeText(this, "Ошибка при обновлении текста", Toast.LENGTH_SHORT).show();
					});
		} else {
			// Если идентификатор записи не определен, выведите сообщение об ошибке
			Toast.makeText(this, "Идентификатор записи не найден", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent (this, wonts_activity.class);
		startActivity(intent);
	}
// пролорпапро
public void onClickDelete(View view){
	// Проверяем, что у нас есть идентификатор записи
	if (zadId != null) {
		DocumentReference noteRef = db.collection("tasks").document(zadId);

		// Удаляем документ из базы данных
		noteRef.delete()
				.addOnSuccessListener(aVoid -> {
					// Успешное удаление записи
					Toast.makeText(this, "Задание успешно удалено", Toast.LENGTH_SHORT).show();

					// После успешного удаления, переходим на другую активность
					Intent intent = new Intent(this, wonts_activity.class);
					startActivity(intent);
				})
				.addOnFailureListener(e -> {
					// Обработка ошибок при удалении
					Toast.makeText(this, "Ошибка при удалении задания", Toast.LENGTH_SHORT).show();
				});
	} else {
		// Если идентификатор записи не определен, выведите сообщение об ошибке
		Toast.makeText(this, "Идентификатор задания не найден", Toast.LENGTH_SHORT).show();
	}
}

}
