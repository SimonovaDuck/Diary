package com.example.diary;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class enter_activity extends Activity {

	private View entering;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter);

		entering = findViewById(R.id.entering);

		// Создаем Handler с задержкой 2 секунды (2000 миллисекунд)
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Изменяем макет текущей активности на другой макет
				setContentView(R.layout.enter_in); // Замените other_layout на имя вашего другого макета
			}
		}, 2000); // 2000 миллисекунд (2 секунды) задержки
	}
}
