package yokastore.youkagames.com.yokastore.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import yokastore.youkagames.com.yokastore.db.converter.Converter;
import yokastore.youkagames.com.yokastore.db.Dao.UserEntityDao;
import yokastore.youkagames.com.yokastore.db.entity.UserEntity;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;

/**
 * Created by songdehua on 2018/10/8.
 */

@Database(entities = {UserEntity.class}, version = 2, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase database;

    public static MyDatabase getInstance(Context context) {
        if (database == null){
            database = Room.databaseBuilder(context, MyDatabase.class, "yokaStore_" + CommonUtil.getUid(context))
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return database;
    }

    public static void createInstance(Context context){
        Room.databaseBuilder(context , MyDatabase.class , "gameplatform_"+ CommonUtil.getUid(context))
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public abstract UserEntityDao getUserEntityDao();

    public static void onDestroy() {
        database = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1 , 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("alter table notify_model add column created_at Text NOT NULL");
            database.execSQL("CREATE TABLE comment_draft (commentId TEXT, draft TEXT)");
        }
    };

}
