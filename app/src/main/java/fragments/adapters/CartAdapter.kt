package fragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.R
import com.example.pizzazz.databinding.CartItemBinding
import database.PizzaEntity
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import pizza_logic.OnFragmentPass
import java.lang.NullPointerException


class CartAdapter : RecyclerView.Adapter<CartAdapter.MenuHolder>() {
    private var cartList = ArrayList<PizzaEntity>()
    private var cartMap = HashMap<PizzaEntity, Int>()
    private lateinit var fragmentPasser: OnFragmentPass
    var flag: Subject<HashMap<PizzaEntity, Int>> = PublishSubject.create()

    class MenuHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = CartItemBinding.bind(v)
        val context = v.context
        fun bind(
            menu: PizzaEntity,
            map: HashMap<PizzaEntity, Int>,
            flag: Subject<HashMap<PizzaEntity, Int>>
        ) = with(binding) {

            cardPizzaName.text = menu.name
            numberOf.text = map[menu].toString()
            Log.e("map", "${menu.id} ${map[menu]}")
            val price = menu.price.toInt() * map[menu]!!
            cardPrice.text = "$price ₽"
            Glide
                .with(context)
                .load(menu.imageUrls[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(cardImage)

            /* button layout (if num of pizza <1 delete from holder)*/
            buttonMinus.setOnClickListener {
                if (map[menu]!! > 1) {
                    map[menu]?.minus(1)?.let { it1 -> map.put(menu, it1) }
                    val price = menu.price.toInt() * (map[menu]!!)
                    cardPrice.text = "$price ₽"
                    numberOf.text = map[menu].toString()
                } else {
                    map.remove(menu)
                }
                flag.onNext(map) /* pass changed map to CartViewModel through observer in CartFragment*/

            }
            buttonPlus.setOnClickListener {
                map[menu]?.plus(1)?.let { it1 -> map.put(menu, it1) }
                val price = menu.price.toInt() * (map[menu]!!)
                cardPrice.text = "$price ₽"
                numberOf.text = map[menu].toString()

                flag.onNext(map)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        fragmentPasser = parent.context as OnFragmentPass
        return MenuHolder(view)

    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        try {
            Log.e("TTT", "${cartList[position].name} $position")
            holder.bind(cartList[position], cartMap, flag)
        } catch (e: NullPointerException) {
            Log.e("hehe", "gotcha")
        }


    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun addPizza(e: PizzaEntity) {
        cartList.add(e)
    }

    fun deleteAll() {
        cartList.clear()
        cartMap.clear()
        notifyDataSetChanged()
    }


    fun updateIfMapChanged() {
        var i = 0
        var j = cartList.size
        while (i < j) {
            if (!cartMap.containsKey(cartList[i])) {
                cartList.removeAt(i)
                notifyItemRemoved(i)
                i = 0
                j = cartList.size
            } else {
                i++
            }
        }
    }

    fun changeMap(response: HashMap<PizzaEntity, Int>) {
        cartMap = response
    }
}
