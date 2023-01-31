package com.example.sportapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("select * from Review")
    LiveData<List<Review>> getAllReviews();

    @Query("select * from Review where reviewId = :reviewId")
    Review getReviewById(String reviewId);

    //onConflict= if the review is already exist, it will update it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Review... reviews);

}
