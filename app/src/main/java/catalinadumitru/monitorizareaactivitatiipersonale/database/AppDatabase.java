package catalinadumitru.monitorizareaactivitatiipersonale.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Activity.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    public  abstract  AppDAO appDAO();
    private static AppDatabase instance;
    private static final String database_name = "projectDB";

    //build the database
    public static synchronized AppDatabase getInstance(Context context){

        if( instance == null){
            instance = createDatabase(context);
        }

        return instance;
    }

    private static AppDatabase createDatabase(final Context context){
        try{
            return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, database_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }catch(Exception e){
            Log.i("Errrroare", e.getMessage());
        }
        return null;
    }
}
