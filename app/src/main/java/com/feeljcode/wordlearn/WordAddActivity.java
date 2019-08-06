package com.feeljcode.wordlearn;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.feeljcode.wordlearn.entity.WordItem;
import com.feeljcode.wordlearn.utils.DataOperation;

public class WordAddActivity extends AppCompatActivity {

    private Context context;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                String tag = (String) msg.obj ;
                if ("success".equals(tag)){
                    finish();//关闭
                }else if ("fail".equals(tag)){
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        };

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
                EditText word = findViewById(R.id.word);
                EditText type = findViewById(R.id.type);
                EditText phoneticSymbol = findViewById(R.id.phoneticSymbol);
                EditText translate = findViewById(R.id.translate);
                String wordVal = word.getText().toString();
                String typeVal = type.getText().toString();
                String phoneticSymbolVal = phoneticSymbol.getText().toString();
                String translateVal = translate.getText().toString();
                if( null == wordVal || null == typeVal ||null == phoneticSymbolVal ||null == translateVal ){
                    Toast.makeText(context,"请补全信息!!",Toast.LENGTH_LONG).show();
                    return;
                }
                WordItem wordItem = new WordItem();
                wordItem.setWord(wordVal);
                wordItem.setTranslate(translateVal);
                wordItem.setType(typeVal);
                wordItem.setPhoneticSymbol(phoneticSymbolVal);
                new Thread(() ->{
                    try {
                        DataOperation.addWordLocal(context,wordItem);
                        Message message = new Message();
                        message.obj = "success";//保存成功
                        mHandler.sendMessage(message);
                    }catch (Exception ex){
                        ex.getStackTrace();

                        Message message = new Message();
                        message.obj = "fail";//保存失败
                        mHandler.sendMessage(message);
                    }

                }).start();

            }
        });



    }



}
