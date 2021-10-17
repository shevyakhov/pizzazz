package fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzazz.R
import com.example.pizzazz.databinding.BottomSheetBinding
import com.example.pizzazz.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var fragmentPasser: OnFragmentPass

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }

}

class BottomFragment : BottomSheetDialogFragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: BottomSheetBinding
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
            passFragment(CartFragment())
            dismiss()
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

