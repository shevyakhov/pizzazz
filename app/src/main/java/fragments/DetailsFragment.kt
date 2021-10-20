package fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.databinding.FragmentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pizza_logic.PizzaModel

class DetailsFragment : BottomSheetDialogFragment() {
    private lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentDetailsBinding
    private val pizzaModel: PizzaModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pizza = pizzaModel.pizzaLive.value

        binding.bottomImage.setOnClickListener {

        }
        context?.let {
            Glide
                .with(it)
                .load(pizza?.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.bottomImage)
        }
        binding.Description.text = pizza?.description
        binding.PizzaName.text = pizza?.name
        binding.btnToCart.text = pizza?.price.toString()
        binding.btnToCart.setOnClickListener {

            if (pizza != null) {
                pizzaModel.addToCart(pizza)
            }
            dismiss()
        }
        binding.swipeBtn.setOnClickListener {
            dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }

}