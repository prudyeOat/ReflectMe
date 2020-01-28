package teamwork.reflectme.roomHelperClasses;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import teamwork.reflectme.model.NoteEntity;


@Dao
public interface NoteDao{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOnlySingleRecord(NoteEntity note);

    @Query("SELECT * FROM note")
    List<NoteEntity> getAll();
    //ResultEntity getAll();
    //LiveData<List<Word>> getAllWords();
    @Query("SELECT * FROM note where note_name LIKE  :name AND note_date LIKE :date AND note_content LIKE :content AND note_path LIKE :imagePath AND note_test LIKE :test " )
    NoteEntity findByName(String name, String date, String content, String imagePath,int test);

    @Query("SELECT COUNT(*) from note")
    int countBudgets();



    @Insert
    void insertAll(NoteEntity... noteEntities);
    // @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Delete
    void delete(NoteEntity note);



}