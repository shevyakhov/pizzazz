package fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pizzazz.activities.PizzaApp
import com.example.pizzazz.databinding.FragmentPreviewBinding
import di.PizzaViewModelFactory
import fragments.adapters.SliderAdapter
import pizza_logic.OnFragmentPass
import vm.AppViewModel
import vm.CartViewModel
import javax.inject.Inject


class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentPreviewBinding
    private lateinit var fragmentPasser: OnFragmentPass

    private lateinit var homeViewModel: AppViewModel
    @Inject
    lateinit var pizzaModelFactory: PizzaViewModelFactory
    private val sliderAdapter = SliderAdapter()

    private val cartModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater)
        return binding.root
    }
    companion object{
        @JvmStatic
        fun newInstance(id: Int) = PreviewFragment().apply {
            arguments = Bundle().apply {
                putInt("pizzaId", id)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PizzaApp.instance.pizzaComponent.inject(this)
        homeViewModel = ViewModelProvider(this,pizzaModelFactory)[AppViewModel::class.java]

        initBinding()
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
                fragmentPasser.onDialog(DetailsFragment.newInstance(arguments?.getInt("pizzaId") as Int))
                Log.e("id", requireArguments().get("pizzaId").toString())
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initBinding() {
        val itemId = arguments?.getInt("pizzaId") as Int
        val pizza = homeViewModel.getPizzaById(itemId)
        sliderAdapter.initList(pizza.imageUrls)
        binding.viewPager.adapter = sliderAdapter

        val size = pizza.imageUrls.size
        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val realPosition = position+1
                binding.pageId.text = "$realPosition/$size"
            }
        })
        binding.btnToCart.setOnClickListener {
            cartModel.addToCart(pizza)
            fragmentPasser.onPopBackStack()
        }
        binding.pizzaName.setOnClickListener {
            fragmentPasser.onPopBackStack()
            fragmentPasser.onDialog(DetailsFragment.newInstance(arguments?.getInt("pizzaId") as Int))
        }
        binding.pizzaName.text = pizza.name
        val price = pizza.price.toInt()
        binding.btnToCart.text = "$price ₽"

    }

}