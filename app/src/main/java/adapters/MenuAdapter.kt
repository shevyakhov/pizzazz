package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.R
import com.example.pizzazz.databinding.MenuItemBinding
import fragments.BottomFragment
import fragments.OnFragmentPass
import pizza_logic.Pizza
import pizza_logic.PizzaEntity


class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    private val menuList = ArrayList<Pizza>()
    lateinit var fragmentPasser: OnFragmentPass
    class MenuHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = MenuItemBinding.bind(v)
        val context = v.context
        fun bind(menu: Pizza) = with(binding) {
            cardPizzaName.text = menu.name
            cardDescription.text = menu.description
            cardPrice.text = menu.price.toString()
            Glide
                .with(context)
                .load(menu.imageUrl)
                .apply(RequestOptions.bitmapTransform( RoundedCorners(50)))
                .into(cardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        fragmentPasser = parent.context as OnFragmentPass
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menuList[position])
        holder.itemView.setOnClickListener {
            fragmentPasser.onPassLiveData(menuList[position])
            fragmentPasser.onDataPass(BottomFragment())
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun addPizza(e: PizzaEntity) {
        menuList.add(e.id -1, Pizza(e.name, e.price, e.imageUrl, e.description))
        notifyDataSetChanged()
    }
}