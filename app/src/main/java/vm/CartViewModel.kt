package vm

import android.util.Log
import androidx.lifecycle.ViewModel
import database.PizzaEntity
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class CartViewModel : ViewModel() {
    private var cartData = ArrayList<PizzaEntity>()
    val observableCart: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()

    fun addToCart(item: PizzaEntity) {
        Log.e("cartdata",cartData.toString())
        var cart = ArrayList<PizzaEntity>()
        cart = cartData
        cart.add(item)
        cartData = cart
        Log.e("cartdata",cartData.toString())
        observableCart.onNext(cartData)
    }
}