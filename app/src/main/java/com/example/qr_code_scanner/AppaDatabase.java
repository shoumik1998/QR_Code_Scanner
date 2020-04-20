package com.example.qr_code_scanner;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class},version = 1)
public abstract class AppaDatabase extends RoomDatabase {
    public  abstract TaskDao taskDao();
}
