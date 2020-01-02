package com.jakchang.savelocation.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jakchang.savelocation.Entity.MemoEntity;

import java.util.List;

@Dao
public interface MemoDao {
    @Query("select * from memolist where date between :fromDate and :toDate and isDeleted='false' order by date desc")
    List<MemoEntity> selectAll(String fromDate, String toDate);

    @Query("select * from memolist where tag=:tag and date between :fromDate and :toDate and isDeleted='false' order by date")
    List<MemoEntity> selectAllByTag(String tag, String fromDate, String toDate);

    @Query("select * from memolist where id=:id")
    MemoEntity selectOne(int id);

    @Query("select * from memolist where isDeleted='true'")
    List<MemoEntity>  selectDeleted();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMemo(MemoEntity memo);

    @Update
    void updateMemo(MemoEntity memo);

    @Delete
    void deleteMemo(MemoEntity... memo);


}
