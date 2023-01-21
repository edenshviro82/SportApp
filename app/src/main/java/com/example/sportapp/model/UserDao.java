package com.example.sportapp.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Project->MODEL->DAO->DB

@Dao
public interface UserDao {
    @Query("select * from User")
    List<User> getAllUsers();
    //LiveData<List<User>> getAllUsers();

    @Query("select * from User where email = :email")
    User getUserByEmail(String email);

    //onConflict= if the user is already exist, it will update it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
