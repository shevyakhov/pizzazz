package vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import database.PizzaEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import org.json.JSONObject
import pizza_logic.PizzaApi
import pizza_logic.SendOrderResponse
import pizza_logic.UserOrder
import repository.PizzaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel(var repository: PizzaRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var pizzaList = ArrayList<PizzaEntity>()
    private lateinit var pizzaApi: PizzaApi
    private var pizzaId: Int = 0


    val observablePizzaList: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()
    fun addPizza(item: Int) {
        pizzaId = item
    }

    private fun passDataToObservable(item: PizzaEntity) {
        var list: ArrayList<PizzaEntity> = pizzaList
        list.add(item)
        pizzaList = list
        observablePizzaList.onNext(pizzaList)

    }

    fun getPizzaWithRetrofit(retrofit: PizzaApi) {

        pizzaApi = retrofit
        compositeDisposable.add(
            pizzaApi.retrievePizzas()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    for (i in it as List<PizzaEntity>) {
                        repository.insertPizza(i)
                        passDataToObservable(i)
                    }
                }, {
                    Log.e("onError", "error")
                })
        )
    }

    fun sendOrder(retrofit: PizzaApi, order: HashMap<PizzaEntity, Int>) {
        pizzaApi = retrofit
        val gson = Gson()
        val json = JSONObject(gson.toJson(order))
        pizzaApi.sendOrder(UserOrder(json))
            .enqueue(object : Callback<SendOrderResponse> {
                override fun onResponse(
                    call: Call<SendOrderResponse>,
                    response: Response<SendOrderResponse>
                ) {
                    Log.e("response", response.toString())
                    Log.e("response", "GOOD")
                }

                override fun onFailure(call: Call<SendOrderResponse>, t: Throwable) {
                    Log.e("failure", "failure", t)
                    Log.e("response", "OH NO")
                }

            })

    }

    fun getFromDb() {
        for (i in repository.getAllPizza()) {
            passDataToObservable(i)
        }
    }

    fun getPizzaById(id: Int): PizzaEntity {
        return repository.getPizzaById(id)
    }

}