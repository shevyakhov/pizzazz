package fragments.adapters

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
import pizza_logic.OnFragmentPass


class CartAdapter : RecyclerView.Adapter<CartAdapter.MenuHolder>() {
    private var cartList = ArrayList<PizzaEntity>()
    private lateinit var cartMap :HashMap<PizzaEntity,Int>
    private lateinit var fragmentPasser: OnFragmentPass

    class MenuHolder(v: View, map: HashMap<PizzaEntity, Int>) : RecyclerView.ViewHolder(v) {
        private val binding = CartItemBinding.bind(v)
        val context = v.context
        fun bind(menu: PizzaEntity, map: HashMap<PizzaEntity, Int>) = with(binding) {
            cardPizzaName.text = menu.name
            numberOf.text = map[menu].toString()
            val price = menu.price.toInt() * map[menu]!!
            cardPrice.text = "$price ₽"
            Glide
                .with(context)
                .load(menu.imageUrls[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(cardImage)

            buttonMinus.setOnClickListener {

                /*TODO menu price*/
                map[menu]?.minus(1)?.let { it1 -> map.put(menu, it1) }
                val price = menu.price.toInt() * (map[menu]!! - 1)
                cardPrice.text = "$price ₽"
                numberOf.text = map[menu].toString()
            }
            buttonPlus.setOnClickListener {
                map[menu]?.plus(1)?.let { it1 -> map.put(menu, it1) }
                val price = menu.price.toInt() * (map[menu]!! + 1)
                cardPrice.text = "$price ₽"
                numberOf.text = map[menu].toString()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        fragmentPasser = parent.context as OnFragmentPass
        return MenuHolder(view, cartMap)

    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(cartList[position], cartMap)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun addPizza(e: PizzaEntity) {
        cartList.add(e)

    }

    fun deleteAll(){
        cartList.clear()
        cartMap.clear()
    }

    fun passMap(map : HashMap<PizzaEntity,Int>){
        cartMap = map
    }


}
