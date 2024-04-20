


package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class note_ex_activity extends Activity {




    private View foot;



    private View rectangle_5;

    private View rectangle_6;
    ;
    private View rectangle_7;

    private View rectangle_1;
    private TextView diary;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_ex);


        //_bg__note = (View) findViewById(R.id._bg__note);
        foot = (View) findViewById(R.id.foot);
        rectangle_5 = (View) findViewById(R.id.rectangle_5);
        rectangle_6 = (View) findViewById(R.id.rectangle_6);
        rectangle_7 = (View) findViewById(R.id.rectangle_7);
        rectangle_1 = (View) findViewById(R.id.rectangle_1);
        diary = (TextView) findViewById(R.id.diary);


        //custom code goes here

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
	
	