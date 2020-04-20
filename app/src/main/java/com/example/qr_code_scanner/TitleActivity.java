package com.example.qr_code_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity {
    private EditText titlrEditText;
    private Button titleBtn;
    private  String sContent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        titlrEditText=(EditText)findViewById(R.id.titleEditID);
        titleBtn=(Button)findViewById(R.id.titleBtnID);

        Bundle bundle=getIntent().getExtras();
        sContent=bundle.getString("content");




        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();

            }
        });

    }

    private void saveTask() {


        class  SaveContent extends AsyncTask<Void,Void,Void>{
           final String  sTitle=titlrEditText.getText().toString();


            @Override
            protected Void doInBackground(Void... voids) {
                Task task=new Task();
                task.setTitle(sTitle);
                task.setContent(sContent);
                Databaseclient.getInstance(getApplicationContext()).getAppaDatabase()
                        .taskDao().insert(task);
                return  null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(TitleActivity.this,MainActivity.class));
                Toast.makeText(getApplicationContext(), " Content Saved" ,Toast.LENGTH_LONG).show();
                finish();
            }
        }
        SaveContent saveContent=new SaveContent();
        saveContent.execute();
    }
}
