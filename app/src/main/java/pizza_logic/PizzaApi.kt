package pizza_logic

import database.PizzaEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PizzaApi {
    @GET("./pizza")
    fun retrievePizzas(): Single<List<PizzaEntity>>

}