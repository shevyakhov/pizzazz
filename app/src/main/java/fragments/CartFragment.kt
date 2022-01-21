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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pizza_logic.OnFragmentPass
import vm.CartViewModel

class CartFragment : Fragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentCartBinding
    private val cartAdapter = CartAdapter()


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
        subscribeOnAdapter()
        bindingInit()
        binding.checkout.setOnClickListener {
            passFragment(EndFragment())
        }
        binding.deleteBtn.setOnClickListener {
            cartModel.deleteAllHashMapData()
            cartAdapter.deleteAll()
            changeCartBtn(cartModel.getCartMap())
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

        cartAdapter.changeMap(cartModel.getCartMap()) /* pass basic map to adapter*/
        addPizza(cartModel.getCartArrayList())                  /* pass List to adapter*/
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
    /* basically change Total sum of order */
    private fun changeCartBtn(cart: HashMap<PizzaEntity, Int>) {

        var sum = 0.0

        for ((key, value) in cart) {
            sum += (key.price * value)
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

    private fun subscribeOnAdapter() {

        cartAdapter.flag.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                cartModel.passNewHashMap(response) /* pass new map to VM*/
                changeCartBtn(response)
                cartAdapter.changeMap(response) /* pass same map back to adapter to rebuild*/
                cartAdapter.updateIfMapChanged()
            }, {throwable -> Log.e("err", throwable.message.toString())})

    }
}
