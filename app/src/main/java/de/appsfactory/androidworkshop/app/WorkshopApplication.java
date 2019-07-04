package de.appsfactory.androidworkshop.app;

import android.app.Application;

import androidx.room.Room;

import de.appsfactory.androidworkshop.persistance.AppDatabase;

public class WorkshopApplication extends Application {

    public static final String DATABASE_NAME = "app_database";

    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppDatabase();
    }

    private void initAppDatabase() {
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
