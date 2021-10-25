package fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.databinding.FragmentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pizza_logic.OnFragmentPass
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

        val pizza = pizzaModel.getPizzaData()


        binding.bottomImage.setOnClickListener {
            fragmentPasser.onFragmentPass(PreviewFragment())
            dismiss()
        }
        context?.let {
            Glide
                .with(it)
                .load(pizza.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.bottomImage)
        }
        binding.Description.text = pizza.description
        binding.PizzaName.text = pizza.name
        binding.btnToCart.text = pizza.price.toString()
        binding.btnToCart.setOnClickListener {
            pizzaModel.addToCart(pizza)
            dismiss()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet = bottomSheetDialog
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = true
            }
        }
        return bottomSheetDialog
    }
}

