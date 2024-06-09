
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		wonts
	 *	@date 		Monday 01st of April 2024 01:01:07 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

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


	public class wonts_activity extends Activity {

	public ImageView stat;

	public ImageView create_new_text;
		private List<View> wontBlocks = new ArrayList<>();
		private FirebaseFirestore db;
		private static final String TAG = "WontsActivity";



		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wonts);

		Log.d(TAG, "onCreate: Started");
		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);

			db = FirebaseFirestore.getInstance(); // Получение экземпляра Cloud Firestore
			// Получение текущего пользователя и загрузка его заметок
			FirebaseAuth mAuth = FirebaseAuth.getInstance();
			FirebaseUser currentUser = mAuth.getCurrentUser();
			if (currentUser != null) {
				String currentUserId = currentUser.getUid();
				loadUserWonts(currentUserId); // Загрузка заметок текущего пользователя
			} else {
				Log.e(TAG, "onCreate: Current user is null");
			}
	
	}
		private void loadUserWonts(String userId) {
			// Получение записей определенного пользователя из базы данных Cloud Firestore
			db.collection("wonts")
					.whereEqualTo("userId", userId) // Фильтр по полю "userId"
					.orderBy("Date", Query.Direction.DESCENDING) // Сортировка по полю "Date" в порядке убывания
					.get()
					.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
						@Override
						public void onComplete(Task<QuerySnapshot> task) {
							if (task.isSuccessful()) {
								Log.d(TAG, "onComplete: Successfully loaded user wonts");
								for (QueryDocumentSnapshot document : task.getResult()) {
									String wontTitle = document.getString("title");
									String wontId = document.getId(); // Получаем идентификатор записи
									// Получаем дату создания заметки
									Timestamp timestamp = (Timestamp) document.get("Date");
									Date date = timestamp.toDate();
									// Добавление каждой записи на экран с указанием идентификатора и даты создания
									addNewWontBlock(wontTitle, wontId, date);
								}
							}
						}
					});
		}
		private void addNewWontBlock(String wontTitle, String wontId, Date Date) {
			Log.d(TAG, "addNewWontBlock: Adding new wont block: " + wontTitle);
			// Инфлейтим макет
			LayoutInflater inflater = LayoutInflater.from(wonts_activity.this);
			View wontCard = inflater.inflate(R.layout.wont_card, null);

			// Найдем TextView в карточке и установим заголовок
			TextView titleTextView = wontCard.findViewById(R.id.title_wont);
			titleTextView.setText(wontTitle);

			// Найдем TextView для noteId и установим значение
			TextView idTextView = wontCard.findViewById(R.id.wont_id);
			idTextView.setText(wontId);

			// Найдем TextView для даты создания и установим значение
			TextView dateTextView = wontCard.findViewById(R.id.date);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()); // Формат даты
			dateTextView.setText(sdf.format(Date));

			// Добавляем карточку в контейнер в меню
			LinearLayout wontLayout = findViewById(R.id.wont_layout);
			wontLayout.addView(wontCard);

			// Сохраняем созданную карточку для возможности последующего обращения к ней
			wontBlocks.add(wontCard);
		}
		public void onClickRealNote(View view) {
			// Получаем макет note_card.xml из родительского View, на котором произошел клик
			View wontCard = (View) view.getParent();

			// Получаем текст заголовка из TextView в макете note_card.xml
			TextView titleTextView = wontCard.findViewById(R.id.title_wont);
			String wontTitle = titleTextView.getText().toString();

			// Получаем noteId из TextView в макете note_card.xml
			TextView idTextView = wontCard.findViewById(R.id.wont_id);
			String wontId = idTextView.getText().toString();

			// Создаем новый Intent
			Intent intent = new Intent(this, wont_ex_activity.class);
			// Передаем заголовок и идентификатор записи в Intent
			intent.putExtra("wont_id", wontId);
			intent.putExtra("wont_title", wontTitle);
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


	public void onClickNewWont(View view){
		Intent intent = new Intent (this, wont_start_activity.class);
		startActivity(intent);
	}

}
	
	