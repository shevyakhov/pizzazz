package vm

import androidx.lifecycle.ViewModel
import database.PizzaEntity
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class CartViewModel : ViewModel() {
    val observableCart: Subject<HashMap<PizzaEntity, Int>> = PublishSubject.create()

    private var cartMap = HashMap<PizzaEntity, Int>()

    fun addToCart(item: PizzaEntity) {
        if (cartMap.containsKey(item)) {
            cartMap.get(item)?.plus(1)?.let { cartMap.put(item, it) }
        } else {

            cartMap.put(item, 1)
        }
        observableCart.onNext(cartMap)
    }

    fun passNewHashMap(newMap: HashMap<PizzaEntity, Int>) {

        cartMap = newMap
        observableCart.onNext(cartMap)
    }

    fun getCartArrayList(): ArrayList<PizzaEntity> {
        val cart = ArrayList<PizzaEntity>()
        for (i in cartMap.keys) {
            cart.add(i)
        }
        return cart
    }

    @JvmName("getCartMap1")
    fun getCartMap(): HashMap<PizzaEntity, Int> {
        return cartMap
    }

    fun deleteAllHashMapData() {
        cartMap.clear()
        observableCart.onNext(cartMap)
    }
}