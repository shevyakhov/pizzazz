package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pizzazz.databinding.FragmentEndBinding
import viewModel.StateViewModel


class EndFragment : Fragment() {
    private lateinit var binding: FragmentEndBinding
    private val stateModel: StateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderSentBtn.setOnClickListener {
            stateModel.deleteFragmentLive.postValue(true)
        }
    }

}