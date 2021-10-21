package pizza_logic

interface PizzaApi {
    fun getDataFromDb(): PizzaDao
}