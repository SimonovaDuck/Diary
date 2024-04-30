


package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class note_ex_activity extends Activity {

private EditText name_of_text_from_author, text_from_author;
private TextView new_zadanie, note_cic_author, note_cic_new;
private FirebaseFirestore db;
private String quoteId, taskId, quoteAuthor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_ex);
        note_cic_new = findViewById(R.id.note_cic_new);
        note_cic_author = findViewById(R.id.note_cic_author);
        new_zadanie = findViewById(R.id.new_zadanie);

        // Получаем айдишник записи из Intent
        Intent intent = getIntent();
        if (intent != null) {
            String noteId = intent.getStringExtra("note_id");
            if (noteId != null) {
                // Инициализируем Firebase
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
                            quoteId = document.getString("quoteId");
                            taskId = document.getString("taskId");
                            // Устанавливаем заголовок и контент записи в соответствующие элементы интерфейса
                            name_of_text_from_author = findViewById(R.id.name_of_text_from_author);
                            text_from_author = findViewById(R.id.text_from_author);
                            name_of_text_from_author.setText(noteTitle);
                            text_from_author.setText(noteContent);
                            // Получаем текст цитаты из базы данных
                            getQuoteText(quoteId);
                            // Получаем текст задания из базы данных
                            getTaskText(taskId);
                        }
                    }
                });
            }
        }
    }
    private void getQuoteText(String quoteId) {
        if (quoteId != null) {
            DocumentReference quoteRef = db.collection("quote").document(quoteId);
            quoteRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Получаем текст цитаты из документа
                        String quoteText = document.getString("text");
                        String quoteAuthor = document.getString("author");
                        // Устанавливаем текст цитаты в соответствующий элемент интерфейса
                        note_cic_new.setText(quoteText);
                        note_cic_author.setText(quoteAuthor);
                    } else {
                        // Если документ не найден, выводим сообщение об ошибке
                        Toast.makeText(this, "Цитата не найдена", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Если не удалось выполнить запрос к базе данных, выводим сообщение об ошибке
                    Toast.makeText(this, "Ошибка при выполнении запроса", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getTaskText(String taskId) {
        if (taskId != null) {
            DocumentReference taskRef = db.collection("bank_ex").document(taskId);
            taskRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Получаем текст задания из документа
                        String taskText = document.getString("text");
                        // Устанавливаем текст задания в соответствующий элемент интерфейса
                        new_zadanie.setText(taskText);
                    } else {
                        // Если документ не найден, выводим сообщение об ошибке
                        Toast.makeText(this, "Задание не найдено", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Если не удалось выполнить запрос к базе данных, выводим сообщение об ошибке
                    Toast.makeText(this, "Ошибка при выполнении запроса", Toast.LENGTH_SHORT).show();
                }
            });
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
	
	