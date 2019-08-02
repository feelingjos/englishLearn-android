package com.feeljcode.wordlearn;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WordAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar

        setContentView(R.layout.activity_word_add);

        Button add = findViewById(R.id.add_word_fun);

        Button exit = findViewById(R.id.exit_word);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//关闭当前activity
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*EditText word = findViewById(R.id.word);
                EditText word = findViewById(R.id.word);
                EditText word = findViewById(R.id.word);
                EditText word = findViewById(R.id.word);*/


            }
        });



    }



}
