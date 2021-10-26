package pizza_logic

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface PizzaApi {
    @GET("./pizza")
    fun retrievePizzas(): Single<List<PizzaEntity>>

    /*fun getDataFromDb(): PizzaDao*/
}