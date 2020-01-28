package teamwork.reflectme.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "result")
public class ResultEntity {
    @ColumnInfo(name = "test_result")
    private int testResult;

    @ColumnInfo(name = "test_type")
    private int test;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "item_date")
    private String date;
    @ColumnInfo(name = "item_note")
    private int note;
    @ColumnInfo(name = "item_answers")
    private String answers;

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getTestResult() {
        return testResult;
    }

    public void setTestResult(int testResult) {
        this.testResult = testResult;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
