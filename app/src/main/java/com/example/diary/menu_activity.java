
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class menu_activity extends Activity {


	public ImageView stat;

	public ImageView create_new_text;
	private List<View> noteBlocks = new ArrayList<>();

	private static final int REQUEST_CODE_NEW_NOTE = 1;

	private FirebaseFirestore db; // Объект для работы с базой данных Cloud Firestore

	private FirebaseAuth auth;



	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		noteBlocks = new ArrayList<>();


		create_new_text = findViewById(R.id.create_new_text);
		stat = findViewById(R.id.stat);
		ImageView exit = findViewById(R.id.exit);

		db = FirebaseFirestore.getInstance(); // Получение экземпляра Cloud Firestore
		// Получение текущего пользователя и загрузка его заметок
		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			String currentUserId = currentUser.getUid();
			loadUserNotes(currentUserId); // Загрузка заметок текущего пользователя
		}
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				auth = FirebaseAuth.getInstance();
				auth.signOut();
				Intent intent = new Intent(menu_activity.this, enteryes_activity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private void loadUserNotes(String userId) {
		db.collection("notes")
				.whereEqualTo("userId", userId) // Фильтр по полю "userId"
				.orderBy("Date", Query.Direction.DESCENDING) // Сортировка по полю "Date"
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							for (QueryDocumentSnapshot document : task.getResult()) {
								String noteTitle = document.getString("title");
								String noteId = document.getId(); // Получаем идентификатор записи
								// Получаем дату создания заметки
								Timestamp timestamp = (Timestamp) document.get("Date");
								Date date = timestamp.toDate();
								// Добавление каждой записи на экран с указанием идентификатора и даты создания
								addNewNoteBlock(noteTitle, noteId, date);
							}
						} else {
							Log.e("Firestore", "Error getting documents: ", task.getException());
						}
					}
				});
	}




	public void onClickRealNote(View view) {
		// Получаем макет note_card.xml из родительского View, на котором произошел клик
		View noteCard = (View) view.getParent();

		// Получаем текст заголовка из TextView в макете note_card.xml
		TextView titleTextView = noteCard.findViewById(R.id.title_from_user);
		String noteTitle = titleTextView.getText().toString();

		// Получаем noteId из TextView в макете note_card.xml
		TextView idTextView = noteCard.findViewById(R.id.note_id);
		String noteId = idTextView.getText().toString();

		// Создаем новый Intent
		Intent intent = new Intent(this, note_ex_activity.class);
		// Передаем заголовок и идентификатор записи в Intent
		intent.putExtra("note_id", noteId);
		intent.putExtra("note_title", noteTitle);
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_NEW_NOTE && resultCode == RESULT_OK) {
			String noteTitle = data.getStringExtra("note_title");
			String noteId = data.getStringExtra("note_id"); // Получаем идентификатор записи
			Date noteDate = (Date) data.getSerializableExtra("note_date"); // Получаем дату создания заметки
			addNewNoteBlock(noteTitle, noteId, noteDate); // Добавляем новую запись в список
		}
	}


	private void addNewNoteBlock(String noteTitle, String noteId, Date Date) {
		// Инфлейтим макет note_card.xml
		LayoutInflater inflater = LayoutInflater.from(this);
		View noteCard = inflater.inflate(R.layout.note_card, null);

		// Найдем TextView в карточке и установим заголовок
		TextView titleTextView = noteCard.findViewById(R.id.title_from_user);
		titleTextView.setText(noteTitle);

		// Найдем TextView для noteId и установим значение
		TextView idTextView = noteCard.findViewById(R.id.note_id);
		idTextView.setText(noteId);

		// Найдем TextView для даты создания и установим значение
		TextView dateTextView = noteCard.findViewById(R.id.date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()); // Формат даты
		dateTextView.setText(sdf.format(Date));

		// Добавляем карточку в контейнер в меню
		LinearLayout menuLayout = findViewById(R.id.menu_layout);
		menuLayout.addView(noteCard);

		// Сохраняем созданную карточку для возможности последующего обращения к ней
		noteBlocks.add(noteCard);
	}

}
	
	