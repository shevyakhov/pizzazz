package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pizzazz.databinding.FragmentCartBinding
import viewModel.StateViewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val stateModel: StateViewModel by activityViewModels()
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
            stateModel.addFragmentLive.postValue(EndFragment())
        }
    }
}