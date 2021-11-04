package fragments

import fragments.adapters.MenuAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzazz.R
import com.example.pizzazz.databinding.FragmentHomeBinding
import database.PizzaEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pizza_logic.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fragmentPasser: OnFragmentPass
    private lateinit var pizzaApi: PizzaApi
    private val compositeDisposable = CompositeDisposable()
    private val menuAdapter = MenuAdapter()
    private val pizzaModel: PizzaModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPizza()
        bindingInit()
        subscribeOnVm()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }


    private fun getPizza() {
        val retrofit = RetrofitInstance(getString(R.string.baseUrl))
        retrofit.configureRetrofit()
        pizzaModel.getPizza(retrofit.pizzaApi)

    }

    private fun bindingInit() {
        binding.apply {
            menuList.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            menuList.adapter = menuAdapter
        }

        binding.checkout.setOnClickListener {
            passFragment(CartFragment())
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                menuAdapter.filter.filter(p0)
                return false
            }
        })

    }

    private fun passFragment(frag: Fragment) {
        fragmentPasser.onFragmentPass(frag)
    }

    private fun subscribeOnVm() {
        pizzaModel.observableCart.subscribe {
            if (it != null) {
                changeCartBtn(it)
            }
        }

        pizzaModel.observablePizzaList.subscribe {
            menuAdapter.addPizza(it.last())
            menuAdapter.notifyItemChanged(menuAdapter.itemCount - 1)
        }

    }


    private fun changeCartBtn(cart: ArrayList<PizzaEntity>) {
        var sum = 0.0
        for (i in cart.indices) {
            sum += cart[i].price
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}


