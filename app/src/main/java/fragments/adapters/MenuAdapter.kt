package fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.R
import com.example.pizzazz.databinding.MenuItemBinding
import fragments.DetailsFragment
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import pizza_logic.ListDiffUtil
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaEntity


class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuHolder>(), Filterable {
    private var menuList = ArrayList<PizzaEntity>()
    private val fullMenuList = ArrayList<PizzaEntity>()
    private lateinit var fragmentPasser: OnFragmentPass

    class MenuHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = MenuItemBinding.bind(v)
        val context = v.context
        fun bind(menu: PizzaEntity) = with(binding) {
            cardPizzaName.text = menu.name
            cardDescription.text = menu.description
            val price = menu.price.toInt()
            cardPrice.text = "$price ₽"
            Glide
                .with(context)
                .load(menu.imageUrls[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
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
           /* fragmentPasser.onDataPass(menuList[position])*/
            fragmentPasser.onDataPass(position)
            fragmentPasser.onDialog(DetailsFragment())
            hideKeyboard(holder.context)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun addPizza(e: PizzaEntity) {
        Log.e("",e.toString())
        menuList.add(e.id - 1, e)
        fullMenuList.add(e.id - 1, e)
    }

    fun changeDataUtil(newList: List<PizzaEntity>) {
        val diffUtil = ListDiffUtil(menuList, newList)
        val results = DiffUtil.calculateDiff(diffUtil)
        menuList = newList as ArrayList<PizzaEntity>
        results.dispatchUpdatesTo(this)
    }

    override fun getFilter(): Filter {
        return filtered
    }

    private val filtered: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<PizzaEntity> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(fullMenuList)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in fullMenuList) {
                    if (item.name.lowercase().contains(filterPattern)) {
                        filteredList.add(item)

                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            changeDataUtil(results.values as List<PizzaEntity>)
        }
    }
    private fun hideKeyboard(ctx: Context) {
        val inputManager: InputMethodManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

}
