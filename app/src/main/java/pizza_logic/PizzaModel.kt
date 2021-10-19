package pizza_logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PizzaModel  : ViewModel() {

    private val pizzaMutableLive = MutableLiveData<Pizza>()
    val pizzaLive = pizzaMutableLive as LiveData<Pizza>


    fun addPizza(item:Pizza){
        pizzaMutableLive.postValue(item)
    }
}