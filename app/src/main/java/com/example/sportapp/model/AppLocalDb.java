package com.example.sportapp.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sportapp.MyApplication;

//entities= tables
@Database(entities = {User.class,Review.class}, version = 3)
//return the Daos
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ReviewDao reviewDao();
}

//just for one static function, none objects of this class
public class AppLocalDb{
    //fallbackToDestructiveMigration = to handle updates,removes old versions related to the current version number
    //build = create DB object
     public static AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName2.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}