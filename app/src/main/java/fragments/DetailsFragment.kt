package fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pizzazz.activities.PizzaApp
import com.example.pizzazz.databinding.FragmentDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import di.PizzaViewModelFactory
import pizza_logic.OnFragmentPass
import vm.AppViewModel
import vm.CartViewModel
import javax.inject.Inject

class DetailsFragment : BottomSheetDialogFragment() {
    private lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentDetailsBinding
    /*TODO*/
    private lateinit var homeViewModel: AppViewModel
    @Inject
    lateinit var pizzaModelFactory: PizzaViewModelFactory

    private val cartModel: CartViewModel by activityViewModels()

    companion object{
        @JvmStatic
        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt("pizzaId", id)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PizzaApp.instance.pizzaComponent.inject(this)
        homeViewModel = ViewModelProvider(this,pizzaModelFactory)[AppViewModel::class.java]


        val itemId = arguments?.getInt("pizzaId") as Int
        val pizza = homeViewModel.getPizzaById(itemId)

        binding.bottomImage.setOnClickListener {
            fragmentPasser.onFragmentPass(PreviewFragment.newInstance(itemId))
            dismiss()
        }
        context?.let {
            Glide
                .with(it)
                .load(pizza.imageUrls[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.bottomImage)
        }
        binding.Description.text = pizza.description
        binding.PizzaName.text = pizza.name
        val price = pizza.price.toInt()
        binding.btnToCart.text = "$price ₽"

        binding.btnToCart.setOnClickListener {
           /* homeViewModel.addToCart(pizza)*/
            cartModel.addToCart(pizza)
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

