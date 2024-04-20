


    package com.example.diary;

    import android.app.Activity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.TextView;

    public class note_example_activity extends Activity {




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
            setContentView(R.layout.note);


            //_bg__note = (View) findViewById(R.id._bg__note);
            foot = (View) findViewById(R.id.foot);
            rectangle_5 = (View) findViewById(R.id.rectangle_5);
            rectangle_6 = (View) findViewById(R.id.rectangle_6);
            rectangle_7 = (View) findViewById(R.id.rectangle_7);
            rectangle_1 = (View) findViewById(R.id.rectangle_1);
            diary = (TextView) findViewById(R.id.diary);


            //custom code goes here

        }
    }
	
	