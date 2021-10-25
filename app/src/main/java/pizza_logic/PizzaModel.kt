package pizza_logic

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class PizzaModel  : ViewModel() {

    private var pizzaData = Pizza()
    private var cartData= ArrayList<Pizza>()
    private fun Pizza(): Pizza {
        return Pizza("",0.0,"","")
    }



    val observableCart: Subject<ArrayList<Pizza>> = PublishSubject.create()
    fun addPizza(item:Pizza){
        pizzaData = item
    }

    fun addToCart(item:Pizza){
        var cart = ArrayList<Pizza>()
        cart = cartData
        cart.add(item)
        cartData = cart
        observableCart.onNext(cartData)
    }
    fun getPizzaData(): Pizza {
        return pizzaData
    }
}