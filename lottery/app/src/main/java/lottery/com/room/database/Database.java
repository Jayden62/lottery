package lottery.com.room.database;

import android.arch.persistence.room.RoomDatabase;
import lottery.com.model.Service;
import lottery.com.room.dao.ServiceDao;

@android.arch.persistence.room.Database(entities = {Service.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract ServiceDao serviceDao();

}