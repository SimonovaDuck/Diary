


package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class note_ex_activity extends Activity {

private EditText name_of_text_from_author, text_from_author;
private FirebaseFirestore db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_ex);

        // Получаем айдишник записи из Intent
        Intent intent = getIntent();
        if (intent != null) {
            String noteId = intent.getStringExtra("note_id");
            if (noteId != null) {
                // Инициализируем Firebase Firestore
                Log.d("NoteActivity", "Note ID: " + noteId);
                db = FirebaseFirestore.getInstance();
                // Получаем документ записи из базы данных
                DocumentReference noteRef = db.collection("notes").document(noteId);
                noteRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Получаем контент записи из документа
                            String noteTitle = document.getString("title");
                            String noteContent = document.getString("content");
                            Log.d("NoteActivity", "Note Title: " + noteTitle);
                            Log.d("NoteActivity", "Note Content: " + noteContent);
                            // Устанавливаем заголовок и контент записи в соответствующие элементы интерфейса
                            name_of_text_from_author = findViewById(R.id.name_of_text_from_author);
                            text_from_author = findViewById(R.id.text_from_author);
                            name_of_text_from_author.setText(noteTitle);
                            text_from_author.setText(noteContent);
                        }
                    } else {
                        Toast.makeText(this, "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            // Если Intent равен null, выводим сообщение об ошибке
            Toast.makeText(this, "Не удалось получить данные", Toast.LENGTH_SHORT).show();
            // И предпринимаем действия по вашему усмотрению
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
//	public void onClickNewNote(View view){
//		Intent intent = new Intent (this,note_activity.class);
//		startActivity(intent);
//	}
// delete доделать
//	public void onClickStatistic(View view){
//		Intent intent = new Intent (this,emotions_activity.class);
//		startActivity(intent);
//	}
}
	
	