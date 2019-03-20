package yokastore.youkagames.com.yokastore.db.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import yokastore.youkagames.com.yokastore.db.entity.UserEntity;

import java.util.List;

/**
 * Created by songdehua on 2018/10/8.
 */
@Dao
public interface UserEntityDao {
    @Query("select * FROM User")
    List<UserEntity> getUserList();

    @Query("select * FROM User WHERE name = :name")
    UserEntity getUserByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity userEntity);

    @Delete()
    void deleteUser(UserEntity userEntity);

    @Update
    void update(UserEntity entity);
}
