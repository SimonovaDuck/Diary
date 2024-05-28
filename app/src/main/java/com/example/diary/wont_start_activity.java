

    package com.example.diary;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.Toast;

    import androidx.annotation.NonNull;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.HashMap;
    import java.util.Map;



    public class wont_start_activity extends Activity {
        private FirebaseFirestore db;
        private EditText text_wont, trecker;
        private Spinner mySpinner;
        // Определение статусов
        private boolean status1 = false;
        private boolean status2 = false;
        private boolean status3 = false;


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.wont_start);

            // Инициализируйте экземпляр FirebaseFirestore
            db = FirebaseFirestore.getInstance();

            // Найдите TextView для цитаты и автора
            text_wont = findViewById(R.id.text_wont);
            trecker = findViewById(R.id.trecker);
            ImageView imageView5 = findViewById(R.id.vector_ek5);
            ImageView imageView7 = findViewById(R.id.vector_ek7);
            ImageView imageView8 = findViewById(R.id.vector_ek8);
            mySpinner = findViewById(R.id.my_spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_items, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

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
        private void resetStatus() {
            status1 = false;
            status2 = false;
            status3 = false;
        }

        public void onClickNewWont(View view) {
            // Получаем текст из полей ввода
            text_wont = findViewById(R.id.text_wont);
            trecker = findViewById(R.id.trecker);
            String title = text_wont.getText().toString();
            String content = trecker.getText().toString();
            // Получаем выбранный элемент из Spinner
            String selectedSpinnerItem = mySpinner.getSelectedItem().toString();

            // Проверяем, что поля не пустые
            if (TextUtils.isEmpty(title)) {
                text_wont.setError("Введите заголовок");
                return;
            }
            if (TextUtils.isEmpty(content)) {
                trecker.setError("Введите содержание");
                return;
            }

            // Получаем текущего пользователя
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Получаем идентификатор текущего пользователя
                String userId = currentUser.getUid();

                // Создаем новую заметку
                Map<String, Object> wont = new HashMap<>();
                wont.put("title", title);
                wont.put("content", content);
                wont.put("userId", userId); // Добавляем идентификатор пользователя
                wont.put("Date", new java.util.Date());
                wont.put("SpinnerItem", selectedSpinnerItem); // Добавляем выбранный элемент из Spinner
                if(status1)
                {
                    wont.put("Status",1);
                }
                else if (status2)
                {
                    wont.put("Status",2);
                }
                else if (status3)
                {
                    wont.put("Status",3);
                }
                else {
                    wont.put("Status",0);
                }

                resetStatus();



                // Добавляем заметку в базу данных
                db.collection("wonts")
                        .add(wont)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String wontId = documentReference.getId(); // Получаем айдишник добавленной заметки
                                // Успешно добавлено
                                Toast.makeText(wont_start_activity.this, "Привычка успешно создана", Toast.LENGTH_SHORT).show();
                                // Очищаем поля ввода после добавления записи
                                text_wont.setText("");
                                trecker.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ошибка при добавлении
                                Toast.makeText(wont_start_activity.this, "Ошибка при создании заметки: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Пользователь не аутентифицирован, обработайте этот случай по вашему усмотрению
                Toast.makeText(wont_start_activity.this, "Пользователь не аутентифицирован", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, wonts_activity.class);
            startActivity(intent);
        }


        public void onClickNotes(View view) {
            Intent intent = new Intent(this, menu_activity.class);
            startActivity(intent);
        }

        public void onClickZads(View view) {
            Intent intent = new Intent(this, zad_activity.class);
            startActivity(intent);
        }

        public void onClickWonts(View view) {
            Intent intent = new Intent(this, wonts_activity.class);
            startActivity(intent);
        }

// delete доделать
        public void onClickDelete(View view) {
            Intent intent = new Intent(this, menu_activity.class);
            startActivity(intent);
        }
    }
	
	