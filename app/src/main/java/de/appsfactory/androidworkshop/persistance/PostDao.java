package de.appsfactory.androidworkshop.persistance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM post")
    List<PostEntity> getAll();

    @Insert
    void insertAll(List<PostEntity> postEntities);

}
