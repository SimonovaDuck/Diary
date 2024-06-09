


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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class note_ex_activity extends Activity {

    private EditText name_of_text_from_author, text_from_author;
    private TextView new_zadanie, note_cic_author, note_cic_new;
    private FirebaseFirestore db;
    private String quoteId, taskId, quoteAuthor, moods_Id, userId;
    private String noteId;
    private TextView dateTextView;
    private ArrayList<String> selectedMoodIds = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_ex);
        note_cic_new = findViewById(R.id.note_cic_new);
        note_cic_author = findViewById(R.id.note_cic_author);
        new_zadanie = findViewById(R.id.new_zadanie);
        dateTextView = findViewById(R.id.date);

        // Получаем айдишник записи из Intent
        Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getStringExtra("note_id");
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
                            userId = document.getString("userId"); // Получаем userId из документа
                            quoteId = document.getString("quoteId");
                            taskId = document.getString("taskId");
                            moods_Id = document.getString("moods_Id");
                            // Устанавливаем заголовок и контент записи в соответствующие элементы интерфейса
                            name_of_text_from_author = findViewById(R.id.name_of_text_from_author);
                            text_from_author = findViewById(R.id.text_from_author);
                            name_of_text_from_author.setText(noteTitle);
                            text_from_author.setText(noteContent);
                            getMoodIds(moods_Id);
                            // Получаем текст цитаты из базы данных
                            getQuoteText(quoteId);
                            // Получаем текст задания из базы данных
                            getTaskText(taskId);
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

    // Метод для получения настроений из базы данных и установки соответствующих изображений
    private void getMoodIds(String moods_Id) {

        // Получаем массив moodId из базы данных по его идентификатору
        DocumentReference moodsRef = db.collection("moods").document(moods_Id);
        moodsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Получаем массив moodId
                    ArrayList<String> moodIds = (ArrayList<String>) document.get("moodId");

                    // Устанавливаем соответствующие изображения эмоций
                    setMoodImages(moodIds);
                } else {
                    // Если документ не найден, выводим сообщение об ошибке
                    Toast.makeText(this, "Данные об эмоциях не найдены", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Если не удалось выполнить запрос к базе данных, выводим сообщение об ошибке
                Toast.makeText(this, "Ошибка при выполнении запроса", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Метод для установки изображений эмоций на основе списка настроений
    private void setMoodImages(ArrayList<String> moodIds) {
        for (String moodId : moodIds) {
            switch (moodId) {
                case "happy":
                    ImageView happyImageView = findViewById(R.id.happy);
                    happyImageView.setImageResource(R.drawable.roundhappy);
                    happyImageView.setTag(true);
                    selectedMoodIds.add("happy");
                    break;
                case "nice":
                    ImageView niceImageView = findViewById(R.id.nice);
                    niceImageView.setImageResource(R.drawable.roundnice);
                    niceImageView.setTag(true);
                    selectedMoodIds.add("nice");
                    break;
                case "love":
                    ImageView loveImageView = findViewById(R.id.love);
                    loveImageView.setImageResource(R.drawable.roundlove);
                    loveImageView.setTag(true);
                    selectedMoodIds.add("love");
                    break;
                case "bue":
                    ImageView bueImageView = findViewById(R.id.bue);
                    bueImageView.setImageResource(R.drawable.roundbue);
                    bueImageView.setTag(true);
                    selectedMoodIds.add("bue");
                    break;
                case "hmm":
                    ImageView hmmImageView = findViewById(R.id.hmm);
                    hmmImageView.setImageResource(R.drawable.roundhmm);
                    hmmImageView.setTag(true);
                    selectedMoodIds.add("hmm");
                    break;
                case "angry":
                    ImageView angryImageView = findViewById(R.id.angry);
                    angryImageView.setImageResource(R.drawable.roundangry);
                    angryImageView.setTag(true);
                    selectedMoodIds.add("angry");
                    break;
                case "cry":
                    ImageView cryImageView = findViewById(R.id.cry);
                    cryImageView.setImageResource(R.drawable.roundcry);
                    cryImageView.setTag(true);
                    selectedMoodIds.add("cry");
                    break;
                // Add cases for other moodIds as needed
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
    public void onClickNewNote(View view){
        // Получаем новые значения текста и контента из элементов интерфейса
        String newTitle = name_of_text_from_author.getText().toString();
        String newContent = text_from_author.getText().toString();

        // Обновляем документ в базе данных
        updateNoteInDatabase(newTitle, newContent);
        updateMoodInDatabase(moods_Id, userId);
    }
    private void updateNoteInDatabase(String newTitle, String newContent) {
        // Проверяем, что у нас есть идентификатор записи
        if (noteId != null) {
            // Получаем ссылку на документ записи в коллекции "notes"
            DocumentReference noteRef = db.collection("notes").document(noteId);

            // Обновляем поля "title" и "content" в документе
            noteRef.update("title", newTitle, "content", newContent)
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
        Intent intent = new Intent (this, menu_activity.class);
        startActivity(intent);
    }
    private void updateMoodInDatabase(String moodsId, String userId) {
        // Проверяем, что у нас есть идентификатор настроения
        if (moodsId != null) {
            // Получаем ссылку на документ настроений в коллекции "moods"
            DocumentReference moodRef = db.collection("moods").document(moodsId);

            // Создаем данные для обновления
            Map<String, Object> mood = new HashMap<>();
            mood.put("userId", userId);
            mood.put("moodId", new ArrayList<>(selectedMoodIds));
            mood.put("Date", new java.util.Date());

            // Обновляем поля в документе
            moodRef.update(mood)
                    .addOnSuccessListener(aVoid -> {
                        // Успешное обновление данных
                        Toast.makeText(this, "Настроение успешно обновлено", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Обработка ошибок при обновлении данных
                        Toast.makeText(this, "Ошибка при обновлении настроения", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Если идентификатор настроения не определен, выводим сообщение об ошибке
            Toast.makeText(this, "Идентификатор настроения не найден", Toast.LENGTH_SHORT).show();
        }
    }


    // delete доделать
    public void onClickDelete(View view){
        // Проверяем, что у нас есть идентификатор записи
        if (noteId != null) {
            // Получаем ссылку на документ записи в коллекции "notes"
            DocumentReference noteRef = db.collection("notes").document(noteId);

            // Удаляем документ из базы данных
            noteRef.delete()
                    .addOnSuccessListener(aVoid -> {
                        // Успешное удаление записи
                        Toast.makeText(this, "Запись успешно удалена", Toast.LENGTH_SHORT).show();

                        // После успешного удаления записи из коллекции "notes" можно удалить соответствующие записи из других коллекций
                        // Удаляем запись из коллекции "moods"
                        DocumentReference moodsRef = db.collection("moods").document(moods_Id);
                        moodsRef.delete()
                                .addOnSuccessListener(aVoid1 -> {
                                    // Успешное удаление записи из коллекции "moods"
                                    Log.d("Firestore", "Запись успешно удалена из коллекции 'moods'");
                                })
                                .addOnFailureListener(e -> {
                                    // Обработка ошибок при удалении записи из коллекции "moods"
                                    Log.e("Firestore", "Ошибка при удалении записи из коллекции 'moods': ", e);
                                });
                        // После удаления всех связанных записей можно перейти на другую активность
                        Intent intent = new Intent(this, menu_activity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Обработка ошибок при удалении
                        Toast.makeText(this, "Ошибка при удалении записи", Toast.LENGTH_SHORT).show();
                    });

        } else {
            // Если идентификатор записи не определен, выведите сообщение об ошибке
            Toast.makeText(this, "Идентификатор записи не найден", Toast.LENGTH_SHORT).show();
        }

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
}
	
	