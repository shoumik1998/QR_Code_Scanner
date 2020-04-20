package com.example.qr_code_scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class FileActivity extends AppCompatActivity {
    private  RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        recyclerView= findViewById(R.id.recyclerID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


getTasks();
    }


    private  void  getTasks(){
        class  GetTasks extends AsyncTask<Void,Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList=Databaseclient
                        .getInstance(getApplicationContext())
                        .getAppaDatabase()
                        .taskDao()
                        .getAll();

                return  taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                ContentAdapter adapter=new ContentAdapter(FileActivity.this,tasks);
                recyclerView.setAdapter(adapter);
            }
        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }
}
