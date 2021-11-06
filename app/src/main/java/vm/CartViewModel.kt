package vm

import android.util.Log
import androidx.lifecycle.ViewModel
import database.PizzaEntity
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class CartViewModel : ViewModel() {
    private var cartData = ArrayList<PizzaEntity>()
    /*val observableCart: Subject<ArrayList<PizzaEntity>> = PublishSubject.create()*/
    val observableCart: Subject<HashMap<PizzaEntity,Int>> = PublishSubject.create()

    val cartMap = HashMap<PizzaEntity,Int>()

    fun addToCart(item: PizzaEntity) {
        if (cartMap.containsKey(item)) {
            cartMap.get(item)?.plus(1)?.let { cartMap.put(item, it) }
        } else
        {
            cartMap.put(item,1)
        }
        observableCart.onNext(cartMap)
    }

    fun getCart(): ArrayList<PizzaEntity> {
        var cart = ArrayList<PizzaEntity>()
        for (i in cartMap.keys){
            cart.add(i)
        }
        return cart
    }

    @JvmName("getCartMap1")
    fun getCartMap(): HashMap<PizzaEntity, Int> {
        return cartMap
    }

    fun deleteAll(){
        cartData.clear()
        observableCart.onNext(cartMap)

        cartMap.clear()
    }
}