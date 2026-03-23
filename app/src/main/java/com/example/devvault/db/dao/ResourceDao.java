package com.example.devvault.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.devvault.db.entity.Resource;
import java.util.List;

@Dao
public interface ResourceDao {
    @Query("SELECT * FROM resources ORDER BY createdAt DESC")
    List<Resource> getAll();

    @Insert
    void insert(Resource resource);

    @Update
    void update(Resource resource);

    @Delete
    void delete(Resource resource);

    @Query("SELECT * FROM resources WHERE id = :id")
    Resource getById(int id);
}
