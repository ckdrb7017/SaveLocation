package com.jakchang.savelocation.Repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jakchang.savelocation.DAO.MemoDao;
import com.jakchang.savelocation.Entity.MemoEntity;

@Database(entities = {MemoEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract MemoDao MemoDao();

    public static AppDatabase getInstance(Context context){
        appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "location")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();

        return appDatabase;
    }
    static final Migration MIGRATION_1_2= new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


            //database.execSQL("CREATE TABLE `table_tmp`");
            database.execSQL("ALTER TABLE memolist ADD COLUMN id INTEGER NOT NULL DEFAULT 0");


        }
    };
}