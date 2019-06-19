package lottery.com.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import lottery.com.model.Service

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun onAddOrUpdateService(service: Service?)

    @Query("SELECT * FROM service")
    fun getAllServices(): MutableList<Service>

    @Query("DELETE FROM service where id=:id")
    fun delete(id: Int?)

    @Query("DELETE FROM service ")
    fun deleteAll()
}