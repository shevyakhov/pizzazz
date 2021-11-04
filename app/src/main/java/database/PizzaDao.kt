package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PizzaDao {
    @Query("SELECT * FROM PizzaEntity")
    fun getAll(): List<PizzaEntity>

    @Query("SELECT * FROM PizzaEntity WHERE id = :index")
    fun findById(index:Int): PizzaEntity

    @Insert
    fun insertPizza( item: PizzaEntity)
    @Delete
    fun delete(item: PizzaEntity)
}