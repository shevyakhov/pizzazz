package fragments

import pizza_logic.MenuAdapter
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
import com.example.pizzazz.databinding.FragmentHomeBinding
import pizza_logic.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fragmentPasser: OnFragmentPass
    private lateinit var database: PizzaApi
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
        bindingInit()
        sendToAdapter(getDbData().getAll())
        subscribeOnVm()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
        database = context as PizzaApi
    }

    private fun sendToAdapter(list: List<PizzaEntity>) {
        for (i in list.indices) {
            menuAdapter.addPizza(list[i])
        }
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
        fragmentPasser.onDataPass(frag)
    }
    private fun subscribeOnVm(){
        pizzaModel.cartLive.observe(viewLifecycleOwner, { cart ->
            changeCartBtn(cart)
        })
    }
    private fun changeCartBtn(cart:ArrayList<Pizza>){
        var sum = 0.0
        for (i in cart.indices){
            sum+=cart[i].price
        }
        if (sum > 0) {
            binding.checkout.text = sum.toString()
            binding.checkout.visibility = View.VISIBLE
        }
        else{
            binding.checkout.text = ""
            binding.checkout.visibility = View.INVISIBLE
        }
    }

    private fun getDbData(): PizzaDao {
        return database.getDataFromDb()
    }


}


