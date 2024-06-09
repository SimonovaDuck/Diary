
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		wont_ex
	 *	@date 		Monday 01st of April 2024 01:01:20 PM
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
	import android.view.View;
	import android.widget.ArrayAdapter;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.Spinner;
	import android.widget.TextView;
	import android.widget.Toast;

	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;

	import java.util.HashMap;
	import java.util.Map;

	public class wont_ex_activity extends Activity {

		private EditText text_wont, trecker;
		private FirebaseFirestore db;
		private String wontId,wontTitle;
		private String noteId;
		private TextView dateTextView;
		private boolean status1 = false;
		private boolean status2 = false;
		private boolean status3 = false;
		private Spinner mySpinner;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wont_ex);
		text_wont = findViewById(R.id.text_wont);
		trecker = findViewById(R.id.trecker);
		ImageView imageView5 = findViewById(R.id.vector_ek5);
		ImageView imageView7 = findViewById(R.id.vector_ek7);
		ImageView imageView8 = findViewById(R.id.vector_ek8);
		mySpinner = findViewById(R.id.my_spinners);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.spinner_items, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner.setAdapter(adapter);


		// Получаем айдишник записи из Intent
		Intent intent = getIntent();
		if (intent != null) {
			wontId = intent.getStringExtra("wont_id");
			wontTitle = intent.getStringExtra("wont_title");
			if (wontId != null && wontTitle != null) {
				// Инициализируем Firebase
				db = FirebaseFirestore.getInstance();
				// Получаем документ записи из базы данных
				DocumentReference noteRef = db.collection("wonts").document(wontId);
				noteRef.get().addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						DocumentSnapshot document = task.getResult();
						if (document.exists()) {
							// Получаем контент записи из документа
							String wontContent = document.getString("content");
							// Устанавливаем заголовок и контент записи в соответствующие элементы интерфейса
							text_wont.setText(wontTitle);
							trecker.setText(wontContent);

							// Установка значения спиннера
							String selectedSpinnerItem = document.getString("SpinnerItem");
							int spinnerIndex = ((ArrayAdapter<String>) mySpinner.getAdapter()).getPosition(selectedSpinnerItem);
							mySpinner.setSelection(spinnerIndex);

							int status = document.getLong("Status").intValue();
							// Устанавливаем соответствующее изображение в зависимости от статуса
							switch (status) {
								case 1:
									imageView5.setImageResource(R.drawable.yes);
									imageView7.setImageResource(R.drawable.no);
									imageView8.setImageResource(R.drawable.no);
									break;
								case 2:
									imageView5.setImageResource(R.drawable.no);
									imageView7.setImageResource(R.drawable.yes);
									imageView8.setImageResource(R.drawable.no);
									break;
								case 3:
									imageView5.setImageResource(R.drawable.no);
									imageView7.setImageResource(R.drawable.no);
									imageView8.setImageResource(R.drawable.yes);
									break;
								default:
									imageView5.setImageResource(R.drawable.no);
									imageView7.setImageResource(R.drawable.no);
									imageView8.setImageResource(R.drawable.no);
									break;
							}
						}
					}
				});
			}
		}
	
	}
		public void onVectorClick(View view) {
			ImageView imageView5 = findViewById(R.id.vector_ek5);
			ImageView imageView7 = findViewById(R.id.vector_ek7);
			ImageView imageView8 = findViewById(R.id.vector_ek8);

			// Сбросить изображения у всех изображений
			imageView5.setImageResource(R.drawable.no);
			imageView7.setImageResource(R.drawable.no);
			imageView8.setImageResource(R.drawable.no);

			// Установить изображение на текущем нажатом изображении
			ImageView clickedImageView = (ImageView) view;
			clickedImageView.setImageResource(R.drawable.yes);
			// Определить, какое изображение было нажато
			if (view.getId() == R.id.vector_ek5) {
				status1=true;
				status2=false;
				status3=false;

			} else if (view.getId() == R.id.vector_ek7) {
				status1=false;
				status2=true;
				status3=false;
			} else if (view.getId() == R.id.vector_ek8) {
				status1=false;
				status2=false;
				status3=true;
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
		String newContent = trecker.getText().toString();
		String newTitle = text_wont.getText().toString();

		// Обновляем документ в базе данных
		updateWontInDatabase(newTitle, newContent);
	}
		private void updateWontInDatabase(String newTitle, String newContent) {
			// Проверяем, что у нас есть идентификатор записи
			if (wontId != null) {
				// Получаем ссылку на документ записи в коллекции "notes"
				DocumentReference noteRef = db.collection("wonts").document(wontId);

				// Обновляем поля "title" и "content" в документе
				noteRef.update("title", newTitle, "content", newContent)
						.addOnSuccessListener(aVoid -> {
							// Успешное обновление данных
							Toast.makeText(this, "Привычка успешно обновлена", Toast.LENGTH_SHORT).show();
						})
						.addOnFailureListener(e -> {
							// Обработка ошибок при обновлении данных
							Toast.makeText(this, "Ошибка при обновлении текста", Toast.LENGTH_SHORT).show();
						});
				// Создаем объект для обновления статуса
				Map<String, Object> statusUpdate = new HashMap<>();
				if (status1) {
					statusUpdate.put("Status", 1);
				} else if (status2) {
					statusUpdate.put("Status", 2);
				} else if (status3) {
					statusUpdate.put("Status", 3);
				} else {
					statusUpdate.put("Status", 0);
				}
				// Обновляем поле "Status" в базе данных
				noteRef.update(statusUpdate)

						.addOnFailureListener(e -> {
							// Обработка ошибок при обновлении статуса
							Toast.makeText(this, "Ошибка при обновлении статуса", Toast.LENGTH_SHORT).show();
						});

				// Обновляем значение спиннера в базе данных
				String selectedSpinnerItem = mySpinner.getSelectedItem().toString();
				noteRef.update("SpinnerItem", selectedSpinnerItem)

						.addOnFailureListener(e -> {
							// Обработка ошибок при обновлении значения спиннера
							Toast.makeText(this, "Ошибка при обновлении значения спиннера", Toast.LENGTH_SHORT).show();
						});
			} else {
				// Если идентификатор записи не определен, выведите сообщение об ошибке
				Toast.makeText(this, "Идентификатор записи не найден", Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent (this, wonts_activity.class);
			startActivity(intent);
		}
// delete доделать
public void onClickDelete(View view){
	// Проверяем, что у нас есть идентификатор записи
	if (wontId != null) {
		// Получаем ссылку на документ записи в коллекции "notes"
		DocumentReference noteRef = db.collection("wonts").document(wontId);

		// Удаляем документ из базы данных
		noteRef.delete()
				.addOnSuccessListener(aVoid -> {
					// Успешное удаление записи
					Toast.makeText(this, "Привычка успешно удалена", Toast.LENGTH_SHORT).show();

					// После успешного удаления, переходим на другую активность
					Intent intent = new Intent(this, wonts_activity.class);
					startActivity(intent);
				})
				.addOnFailureListener(e -> {
					// Обработка ошибок при удалении
					Toast.makeText(this, "Ошибка при удалении привычки", Toast.LENGTH_SHORT).show();
				});
	} else {
		// Если идентификатор записи не определен, выведите сообщение об ошибке
		Toast.makeText(this, "Идентификатор привычки не найден", Toast.LENGTH_SHORT).show();
	}

}
}
	
	