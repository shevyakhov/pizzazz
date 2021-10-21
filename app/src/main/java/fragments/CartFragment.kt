package fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzazz.databinding.FragmentCartBinding
import pizza_logic.OnFragmentPass

class CartFragment : Fragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cartBtn.setOnClickListener {
           passFragment(EndFragment())
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }
    private fun passFragment(frag: Fragment) {
        fragmentPasser.onDataPass(frag)
    }
}