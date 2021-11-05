package repository

import database.AppDatabase
import database.PizzaDao
import database.PizzaEntity

class PizzaRepository(db: AppDatabase) {

    private var dao = db.getDao()

    fun insertPizza(item: PizzaEntity) {
        dao.insertPizza(item)

    }
    fun deletePizza(item: PizzaEntity) {
       dao.delete(item)
    }
    fun getPizzaById(id: Int): PizzaEntity {
        return dao.findById(id)
    }
    fun getAllPizza(): List<PizzaEntity> {
        return dao.getAll()
    }
}