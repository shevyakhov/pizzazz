package fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzazz.databinding.FragmentCartBinding
import database.PizzaEntity
import fragments.adapters.CartAdapter
import pizza_logic.OnFragmentPass
import vm.CartViewModel

class CartFragment : Fragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentCartBinding
    private val cartAdapter = CartAdapter()

    /**
    * Not finished just testing layout
    *
    * */
    private val cartModel: CartViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingInit()
        binding.checkout.setOnClickListener {
           passFragment(EndFragment())
        }
        binding.deleteBtn.setOnClickListener {
            cartModel.deleteAll()
            cartAdapter.deleteAll()
        }

         cartModel.observableCart.subscribe {
            changeCartBtn(it)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }


    private fun bindingInit() {
        binding.apply {
            cartList.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            cartList.adapter = cartAdapter
        }
        cartAdapter.passMap(cartModel.getCartMap())
       addPizza(cartModel.getCart())
       changeCartBtn(cartModel.getCartMap())

    }
    private fun passFragment(frag: Fragment) {
        fragmentPasser.onFragmentPass(frag)
    }

    private fun addPizza(cart: ArrayList<PizzaEntity>) {
        for (i in cart) {
            cartAdapter.addPizza(i)
        }
    }

    private fun changeCartBtn(cart: HashMap<PizzaEntity, Int>) {
        var sum = 0.0

        for ((key, value) in cart) {
            sum+= (key.price * value)
        }
        val total = sum.toInt()
        if (total > 0) {
            binding.checkout.text = "$total ₽"
            binding.checkout.visibility = View.VISIBLE
        } else {
            binding.checkout.text = ""
            binding.checkout.visibility = View.INVISIBLE
        }
    }
}