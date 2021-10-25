package pizza_logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.*
import kotlin.collections.ArrayList

class PizzaModel  : ViewModel() {

    private val pizzaMutableLive = MutableLiveData<Pizza>()
    private val cartMutableLive = MutableLiveData<ArrayList<Pizza>>()
    val pizzaLive = pizzaMutableLive as LiveData<Pizza>
    val cartLive = cartMutableLive as LiveData<ArrayList<Pizza>>
    var pizzaData = Pizza()
    private var cartData= ArrayList<Pizza>()
    private fun Pizza(): Pizza {
        return Pizza("",0.0,"","")
    }


    val observablePizza = Observable.just(pizzaData)
    /*val observableCart: Observable<ArrayList<Pizza>> = Observable.just(cartData)*/
    val observableCart: Subject<ArrayList<Pizza>> = PublishSubject.create()
    fun addRxPizza(item:Pizza){
        pizzaData = item
    }
    fun addPizza(item:Pizza){
        pizzaMutableLive.postValue(item)
    }
    fun addToCart(item:Pizza){

        var cart = ArrayList<Pizza>()
        if (cartLive.value != null) {
            cart = cartLive.value!!
            }

        cart.add(item)
        cartMutableLive.postValue(cart)
    }
    fun addToCartRx(item:Pizza){

        var cart = ArrayList<Pizza>()
        cart = cartData
        cart.add(item)
        cartData = cart
        observableCart.onNext(cartData)
    }
}