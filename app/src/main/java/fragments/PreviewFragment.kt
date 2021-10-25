package fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzazz.R
import com.example.pizzazz.databinding.FragmentDetailsBinding
import com.example.pizzazz.databinding.FragmentPreviewBinding
import pizza_logic.OnFragmentPass
import android.widget.Toast





class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentPreviewBinding
    private lateinit var fragmentPasser: OnFragmentPass
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToCart.setOnClickListener {
            fragmentPasser.onPopBackStack()
            fragmentPasser.onDialog(DetailsFragment())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPasser = context as OnFragmentPass
    }
    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                fragmentPasser.onPopBackStack()
                fragmentPasser.onDialog(DetailsFragment())
                return@OnKeyListener true
            }
            false
        })
    }

}