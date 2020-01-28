package teamwork.reflectme.roomHelperClasses;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import teamwork.reflectme.model.ResultEntity;

@Dao
public interface ScoreDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOnlySingleRecord(ResultEntity result);

    @Query("SELECT * FROM result")
    List<ResultEntity> getAll();
   //ResultEntity getAll();
    //LiveData<List<Word>> getAllWords();
    @Query("SELECT * FROM result where test_result LIKE  :testResult AND test_type LIKE :test AND item_date LIKE :date AND item_note LIKE :note AND item_answers LIKE :answers" )
   ResultEntity findByName(int testResult, int test, String date,int note,String answers);

    @Query("SELECT COUNT(*) from result")
    int countBudgets();

    @Query("SELECT * FROM result where item_date LIKE :date")
    ResultEntity getResult(String date);
    @Insert
    void insertAll(ResultEntity... resultEntities);
    // @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Delete
    void delete(ResultEntity result);

}