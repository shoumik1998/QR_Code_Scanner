package com.example.qr_code_scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    public static  TextView textView;
    private Button take_pic;
    private Context context;
    Task task;

    private CircleMenu circleMenu;


    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int STORAGE_REQUEST_CODE = 3;



    String cameraPermission[];
    String storagePermission[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       textView =  findViewById(R.id.TextViewID);
       take_pic=findViewById(R.id.btnID);


         final Task task1= (Task)getIntent().getSerializableExtra("task");
        circleMenu=findViewById(R.id.circleMenuID);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.plus,R.drawable.multiply)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.file)
                .addSubMenu(Color.parseColor("#6d4c41"),R.drawable.addfile)
                .addSubMenu(Color.parseColor("#1a237e"),R.drawable.share)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                   if (index==0){
                       Intent intent=new Intent(getApplicationContext(),FileActivity.class);
                       startActivityForResult(intent,50);
                   }
                   if (index==1){
                       if (!TextUtils.isEmpty(textView.getText().toString())) {
                           Intent intent = new Intent(MainActivity.this, TitleActivity.class);
                           intent.putExtra("content", textView.getText().toString());
                           startActivity(intent);
                       } else {
                           Toast.makeText(getApplicationContext(), "Nothing to save", Toast.LENGTH_SHORT).show();
                       }

                   }
                   if (index==2){
                       if (!TextUtils.isEmpty(textView.getText().toString())) {
                           go_to_media();
                       } else {
                           Toast.makeText(getApplicationContext(), "Noting To Share", Toast.LENGTH_SHORT).show();
                       }

                   }
                    }
                });



        LocalBroadcastManager.getInstance(this).registerReceiver(mContentReceiver,new IntentFilter("content"));

        

        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!camera_permission_check()) {
                    camera_permission_request();
                } else {
                    pick_camera();
                }

            }
        });


    }

   public BroadcastReceiver mContentReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             task= (Task) intent.getSerializableExtra("task");
            textView.setText(task.getContent());

        }
    };



    private void pick_camera() {
        startActivity(new Intent(MainActivity.this,Scan_Code.class));
    }



    private void camera_permission_request() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean camera_permission_check() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }



    private void go_to_media() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
        startActivity(Intent.createChooser(intent, "Share Using"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pick_camera();
                    } else {
                        Toast.makeText(this, "Permission denied !!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

        }
    }

    }

