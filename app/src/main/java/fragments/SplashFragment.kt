package fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pizzazz.R
import com.example.pizzazz.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateSplashScreen()
    }

    private fun animateSplashScreen() {
        binding.splashLogo.animate().alpha(0.0f).duration = 0
        Handler(Looper.getMainLooper()).postDelayed({
            binding.splashLogo.animate().alpha(1.0f).duration = 500
        }, 300)
        Handler(Looper.getMainLooper()).postDelayed({
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)
                ?.add(R.id.fragmentHolder, HomeFragment())?.commit()
        }, 1500)
    }

}