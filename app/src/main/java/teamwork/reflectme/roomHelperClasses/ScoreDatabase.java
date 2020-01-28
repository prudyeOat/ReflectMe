package teamwork.reflectme.roomHelperClasses;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import teamwork.reflectme.model.ResultEntity;

@Database(entities = {ResultEntity.class}, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {

    private static ScoreDatabase INSTANCE;

    public abstract ScoreDao scoreDao();

    public static ScoreDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),ScoreDatabase.class, "score-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            // .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
