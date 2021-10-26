package pizza_logic

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class PizzaModel  : ViewModel() {

    private var pizzaData = Pizza()
    private var cartData= ArrayList<PizzaEntity>()
    private fun Pizza(): PizzaEntity {
        return PizzaEntity(-1,"",0.0, listOf(""),"")
    }



    val observableCart: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()
    fun addPizza(item:PizzaEntity){
        pizzaData = item
    }

    fun addToCart(item:PizzaEntity){
        var cart = ArrayList<PizzaEntity>()
        cart = cartData
        cart.add(item)
        cartData = cart
        observableCart.onNext(cartData)
    }
    fun getPizzaData(): PizzaEntity {
        return pizzaData
    }
}