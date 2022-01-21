package vm

import android.util.Log
import androidx.lifecycle.ViewModel
import database.PizzaEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import pizza_logic.PizzaApi
import repository.PizzaRepository

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

    fun getFromDb() {
        for (i in repository.getAllPizza()) {
            passDataToObservable(i)
        }
    }

    fun getPizzaById(id: Int): PizzaEntity {
        return repository.getPizzaById(id)
    }

}