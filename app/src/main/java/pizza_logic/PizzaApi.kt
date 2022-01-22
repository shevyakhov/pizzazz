package pizza_logic

import retrofit2.Call
import database.PizzaEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PizzaApi {
    @GET("./pizza")
    fun retrievePizzas(): Single<List<PizzaEntity>>
    @POST("./fakeOrder") /* does not work*/
    fun sendOrder(
        @Body order: UserOrder
    ): Call<SendOrderResponse>

}