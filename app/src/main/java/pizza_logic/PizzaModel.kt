package pizza_logic

import android.util.Log
import androidx.lifecycle.ViewModel
import database.PizzaEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import repository.PizzaRepository

class PizzaModel(repository: PizzaRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var pizzaList = ArrayList<PizzaEntity>()
    private lateinit var pizzaApi: PizzaApi
    private var pizzaId:Int = 0
    private var cartData = ArrayList<PizzaEntity>()
    private fun Pizza(): PizzaEntity {
        return PizzaEntity(-1, "", 0.0, listOf(""), "")
    }


    val observableCart: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()
    val observablePizzaList: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()
    fun addPizza(item: Int) {
        pizzaId = item
    }

    fun addToCart(item: PizzaEntity) {
        var cart = ArrayList<PizzaEntity>()
        cart = cartData
        cart.add(item)
        cartData = cart
        observableCart.onNext(cartData)
    }
    fun getPizzaWeb(item: PizzaEntity) {
        var list = ArrayList<PizzaEntity>()
        list = pizzaList
        list.add(item)
        pizzaList = list
        observablePizzaList.onNext(pizzaList)
    }

    fun getPizzaData(): Int {
        return pizzaId
    }

    fun getPizza(retrofit: PizzaApi) {

        pizzaApi = retrofit
        compositeDisposable.add(
            pizzaApi.retrievePizzas()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    for (i in it as List<PizzaEntity> ){
                        getPizzaWeb(i)
                    }
                }, {
                    Log.e("onError", "error")
                })
        )
    }


    fun getPizzaList(): List<PizzaEntity> {
        return pizzaList
    }
}