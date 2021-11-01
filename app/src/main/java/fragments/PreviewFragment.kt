package fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.pizzazz.databinding.FragmentPreviewBinding
import fragments.adapters.SliderAdapter
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaModel


class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentPreviewBinding
    private lateinit var fragmentPasser: OnFragmentPass
    private val pizzaModel: PizzaModel by activityViewModels()
    private val sliderAdapter = SliderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                fragmentPasser.onDialog(DetailsFragment())
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initBinding() {
        val itemId = pizzaModel.getPizzaData()
        val pizza = pizzaModel.getPizzaList()[itemId]
        sliderAdapter.initList(pizza.imageUrls as ArrayList<String>)
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
            fragmentPasser.onPopBackStack()
        }
        binding.pizzaName.setOnClickListener {
            fragmentPasser.onPopBackStack()
            fragmentPasser.onDialog(DetailsFragment())
        }
        binding.pizzaName.text = pizza.name
        val price = pizza.price.toInt()
        binding.btnToCart.text = "$price ₽"

    }

}