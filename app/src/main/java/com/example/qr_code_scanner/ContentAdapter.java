package com.example.qr_code_scanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context context;
    private List<Task> taskList;



    public ContentAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.file_list,parent,false);

        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Task t=taskList.get(position);
        holder.textView_File_Name.setText(t.getTitle());

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView_File_Name;
        public ContentViewHolder(@NonNull final View itemView) {
            super(itemView);

            textView_File_Name=itemView.findViewById(R.id.fileNameID);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Are You sure ");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Task task1=taskList.get(getAdapterPosition());
                            deleteContent(task1);
                            taskList.remove(getAdapterPosition());
                            notifyDataSetChanged();



                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();


                    return true;
                }
            });

        }

        private void deleteContent(final  Task task) {
            class  DeleteContent extends AsyncTask<Void,Void,Void>{

                @Override
                protected Void doInBackground(Void... voids) {

                    Databaseclient.getInstance(context).getAppaDatabase()
                            .taskDao().delete(task);
                    return null;
                }
            }
            new DeleteContent().execute();
        }

        @Override
        public void onClick(View v) {
            Task task=taskList.get(getAdapterPosition());
            Intent intent=new Intent("content");
            intent.putExtra("task",task);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            ((AppCompatActivity)context).finish();

        }


        
    }


}
