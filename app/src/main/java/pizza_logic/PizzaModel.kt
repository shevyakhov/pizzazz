package pizza_logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PizzaModel  : ViewModel() {

    private val pizzaMutableLive = MutableLiveData<Pizza>()
    private val cartMutableLive = MutableLiveData<ArrayList<Pizza>>()
    val pizzaLive = pizzaMutableLive as LiveData<Pizza>
    val cartLive = cartMutableLive as LiveData<ArrayList<Pizza>>

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
}