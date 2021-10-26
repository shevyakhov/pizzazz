package fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.databinding.FragmentPreviewBinding
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaModel


class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentPreviewBinding
    private lateinit var fragmentPasser: OnFragmentPass
    private val pizzaModel: PizzaModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pizza = pizzaModel.getPizzaData()
        binding.btnToCart.setOnClickListener {
            fragmentPasser.onPopBackStack()
            fragmentPasser.onDialog(DetailsFragment())
        }
        binding.pizzaName.setOnClickListener {
            fragmentPasser.onPopBackStack()
            fragmentPasser.onDialog(DetailsFragment())
        }
        context?.let {
            Glide
                .with(it)
                .load(pizza.imageUrls[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.imagePreview)
        }
        binding.pizzaName.text = pizza.name
            val price = pizza.price.toInt()
        binding.btnToCart.text =  "$price ₽"

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }
    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                fragmentPasser.onPopBackStack()
                fragmentPasser.onDialog(DetailsFragment())
                return@OnKeyListener true
            }
            false
        })
    }

}