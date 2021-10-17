package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizzazz.R
import com.example.pizzazz.databinding.MenuItemBinding
import viewModel.PizzaDao

/*TODO PIZZADAO HOLDER FIX*/
class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    private val menuList = ArrayList<PizzaDao>()

    class MenuHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = MenuItemBinding.bind(v)
        val context = v.context

        /*TODO DATABASE*/
        fun bind(menu: PizzaDao) = with(binding) {
            cardPizzaName.text = menu.toString()
            cardDescription.text = menu.toString()
            cardPrice.text = menu.toString()
            Glide
                .with(context)
                .load(menu.toString())
                .into(cardImage)
            /*TODO DATABASE*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menuList[position])
        holder.itemView.setOnClickListener {
            Log.e("item", holder.toString())
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}