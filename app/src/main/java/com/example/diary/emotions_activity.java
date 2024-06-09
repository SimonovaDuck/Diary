package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


public class emotions_activity extends Activity {

	// Добавляем ссылку на базу данных Firestore
	private FirebaseFirestore db;

	// Добавляем переменные для отображения настроений
	private ImageView happyImage;
	private ImageView niceImage;
	private ImageView loveImage;
	private ImageView bueImage;
	private ImageView hmmImage;
	private ImageView angryImage;
	private ImageView cryImage;
	private String currentUserId;
	// Добавьте переменные для других настроений по аналогии

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emotions);

		// Инициализируем базу данных Firestore
		db = FirebaseFirestore.getInstance();
		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			 currentUserId = currentUser.getUid();
		}

			// Инициализируем переменные для отображения настроений
		happyImage = findViewById(R.id.happyImage);
		niceImage = findViewById(R.id.niceImage);
		loveImage = findViewById(R.id.loveImage);
		bueImage = findViewById(R.id.bueImage);
		hmmImage = findViewById(R.id.hmmImage);
		angryImage = findViewById(R.id.angryImage);
		cryImage = findViewById(R.id.cryImage);
		// Инициализируйте другие переменные для отображения настроений по аналогии

		CalendarView calendarView = findViewById(R.id.calendarView);
		calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				String date = dayOfMonth + "/" + (month + 1) + "/" + year;
				hideAllMoods();
				// Вызываем метод для загрузки настроений пользователя на выбранную дату
				loadMoodsForDate(date);
			}
		});
		// Вызов метода для отладки
		logAllMoodsDates();
	}

	// Метод для загрузки настроений пользователя на выбранную дату
	private void loadMoodsForDate(String date) {
		Log.d("EmotionsActivity", "Получена строка даты: " + date);

		// Преобразуем строку с датой в объект java.util.Date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date selectedDate = null;
		try {
			selectedDate = dateFormat.parse(date);
			Log.d("EmotionsActivity", "Разобранная дата: " + selectedDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Устанавливаем время на полночь
		Calendar calendar = Calendar.getInstance();
		if (selectedDate != null) {
			calendar.setTime(selectedDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			selectedDate = calendar.getTime();
			Log.d("EmotionsActivity", "Дата установлена на полночь: " + selectedDate.toString());
		}

		// Дата начала и конца дня
		Timestamp startOfDay = new Timestamp(selectedDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Timestamp endOfDay = new Timestamp(calendar.getTime());

		Log.d("EmotionsActivity", "Начало дня: " + startOfDay.toDate().toString());
		Log.d("EmotionsActivity", "Конец дня: " + endOfDay.toDate().toString());
		Log.d("EmotionsActivity", "Идентификатор текущего пользователя: " + currentUserId);

		// Выполняем запрос к базе данных, чтобы получить настроения пользователя на выбранную дату
		db.collection("moods")
				.whereGreaterThanOrEqualTo("Date", startOfDay)
				.whereLessThan("Date", endOfDay)
				.whereEqualTo("userId", currentUserId)
				.get()
				.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
					@Override
					public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
						Log.d("EmotionsActivity", "Запрос выполнен успешно.");
						if (!queryDocumentSnapshots.isEmpty()) {
							Log.d("EmotionsActivity", "Документы найдены: " + queryDocumentSnapshots.size());
							for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
								List<String> moodIds = (List<String>) document.get("moodId");
								Log.d("EmotionsActivity", "Найдены настроения для даты: " + moodIds.toString());
								displayMoods(moodIds);
							}
							// Добавим лог для отображения существующих дат в базе данных с выбранной датой
							for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
								Date moodDate = document.getDate("Date");
								Log.d("EmotionsActivity", "Дата в базе данных: " + moodDate.toString());
							}
						} else {
							Log.d("EmotionsActivity", "На выбранную дату отсутствуют настроения.");
							hideAllMoods();
						}
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.e("EmotionsActivity", "Ошибка при получении документов из Firestore: ", e);
					}
				});
	}

	private void logAllMoodsDates() {
		db.collection("moods")
				.get()
				.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
					@Override
					public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
						Log.d("EmotionsActivity", "Всего документов в коллекции: " + queryDocumentSnapshots.size());
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
							Date moodDate = document.getDate("Date");
							String userId = document.getString("userId");
							Log.d("EmotionsActivity", "Документ ID: " + document.getId() + ", Дата: " + dateFormat.format(moodDate) + ", Идентификатор пользователя: " + userId);
						}
					}
				});
	}






	// Метод для отображения изображений настроений
	private void displayMoods(List<String> moodIds) {
		Log.d("EmotionsActivity", "Полученные идентификаторы настроений: " + moodIds.toString());
		for (String moodId : moodIds) {
			switch (moodId) {
				case "happy":
					happyImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "happy" для happyImage
					break;
				case "nice":
					niceImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "nice" для niceImage
					break;
				case "love":
					loveImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "love" для loveImage
					break;
				case "bue":
					bueImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "bue" для bueImage
					break;
				case "hmm":
					hmmImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "hmm" для hmmImage
					break;
				case "angry":
					angryImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "angry" для angryImage
					break;
				case "cry":
					cryImage.setVisibility(View.VISIBLE);
					// Установите изображение настроения "cry" для cryImage
					break;
				// Добавьте другие case для остальных настроений
			}
		}
	}

	// Метод для скрытия всех изображений настроений
	private void hideAllMoods() {
		happyImage.setVisibility(View.GONE);
		niceImage.setVisibility(View.GONE);
		loveImage.setVisibility(View.GONE);
		bueImage.setVisibility(View.GONE);
		hmmImage.setVisibility(View.GONE);
		angryImage.setVisibility(View.GONE);
		cryImage.setVisibility(View.GONE);
	}

	// Метод для получения идентификатора текущего пользователя
	private String getCurrentUserId() {
		// Получаем текущего пользователя Firebase
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser != null) {
			// Если пользователь аутентифицирован, возвращаем его идентификатор
			return currentUser.getUid();
		} else {
			// Если пользователь не аутентифицирован, возвращаем пустую строку
			return "";
		}
	}


	// Методы для обработки нажатий на кнопки
	public void onClickNotes(View view){
		Intent intent = new Intent(this, menu_activity.class);
		startActivity(intent);
	}

	public void onClickZads(View view){
		Intent intent = new Intent(this, zad_activity.class);
		startActivity(intent);
	}

	public void onClickWonts(View view){
		Intent intent = new Intent(this, wonts_activity.class);
		startActivity(intent);
	}

	public void onClickNewNote(View view){
		Intent intent = new Intent(this, note_activity.class);
		startActivity(intent);
	}

	public void onClickStatistic(View view){
		Intent intent = new Intent(this, emotions_activity.class);
		startActivity(intent);
	}
}
