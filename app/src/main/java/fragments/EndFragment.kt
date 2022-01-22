package fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pizzazz.R
import com.example.pizzazz.activities.PizzaApp
import com.example.pizzazz.databinding.FragmentEndBinding
import di.PizzaViewModelFactory
import pizza_logic.OnFragmentPass
import pizza_logic.RetrofitInstance
import vm.AppViewModel
import vm.CartViewModel
import javax.inject.Inject


class EndFragment : Fragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentEndBinding
    private lateinit var appViewModel: AppViewModel
    private val cartModel: CartViewModel by activityViewModels()

    @Inject
    lateinit var pizzaModelFactory: PizzaViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEndBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PizzaApp.instance.pizzaComponent.inject(this)
        appViewModel = ViewModelProvider(this, pizzaModelFactory)[AppViewModel::class.java]
        binding.orderSentBtn.setOnClickListener {
            deleteFragment()

        }
        val retrofit = RetrofitInstance(getString(R.string.baseUrl))
        retrofit.configureRetrofit()
        appViewModel.sendOrder(
            retrofit.pizzaApi,
            cartModel.getCartMap()
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }

    private fun passFragment(frag: Fragment) {
        fragmentPasser.onFragmentPass(frag)
    }

    private fun deleteFragment() {
        cartModel.deleteAllHashMapData()
        fragmentPasser.onDataDelete()
    }
    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                fragmentPasser.onDataDelete()
                cartModel.deleteAllHashMapData()
                return@OnKeyListener true
            }
            false
        })
    }
}