package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pizzazz.R
import com.example.pizzazz.databinding.BottomSheetBinding
import com.example.pizzazz.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import viewModel.StateViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val stateModel: StateViewModel by activityViewModels()
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
        binding.btn.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.bottomSheet, BottomFragment())
                .commit()
        }
    }

}

class BottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding
    private val stateModel: StateViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = BottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomBackground.setOnClickListener {
            dismiss()
        }
        binding.button.setOnClickListener {
            stateModel.addFragmentLive.postValue(CartFragment())
            dismiss()
        }

    }

}

