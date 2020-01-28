package teamwork.reflectme.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class NoteEntity {
    @ColumnInfo(name = "note_name")
    private String name;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "note_date")
    private String date;
    @ColumnInfo(name = "note_content")
    private String content;
    @ColumnInfo(name = "note_path")
    private String imagePath;
    @ColumnInfo(name = "note_test")
    private int test;

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
