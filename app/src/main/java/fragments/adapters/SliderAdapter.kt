package fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.R
import fragments.DetailsFragment
import com.example.pizzazz.databinding.SliderItemBinding
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaEntity


class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderHolder>() {
    private var imageList = ArrayList<String>()

    class SliderHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = SliderItemBinding.bind(v)
        val context = v.context
        fun bind(menu: String) = with(binding) {
            Glide
                .with(context)
                .load(menu)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun initList(list:ArrayList<String>){
        imageList = list
    }

}
