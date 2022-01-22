package fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzazz.databinding.FragmentEndBinding
import pizza_logic.OnFragmentPass


class EndFragment : Fragment() {
    lateinit var fragmentPasser: OnFragmentPass
    private lateinit var binding: FragmentEndBinding

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
            deleteFragment()

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }

    private fun passFragment(frag: Fragment) {
        fragmentPasser.onFragmentPass(frag)
    }
    private fun deleteFragment() {
        fragmentPasser.onDataDelete()
    }
}