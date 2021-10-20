package fragments

import adapters.MenuAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzazz.databinding.FragmentHomeBinding
import pizza_logic.PizzaDatabase
import pizza_logic.PizzaEntity
import pizza_logic.PizzaModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var fragmentPasser: OnFragmentPass
    private val menuAdapter = MenuAdapter()
    private val pizzaModel: PizzaModel by activityViewModels()
    private var pizzaDb = PizzaDatabase
    private var dao = pizzaDb.pizzaDao
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
        sendToAdapter(dao.getAll())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
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
        binding.button.setOnClickListener {
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

}


