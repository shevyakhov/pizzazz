package com.example.pizzazz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.pizzazz.R
import com.example.pizzazz.databinding.ActivityMainBinding
import fragments.HomeFragment
import fragments.OnFragmentPass

class MainActivity : AppCompatActivity(), OnFragmentPass{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.SplashTheme)
        setContentView(binding.root)
        startMainFragment()
    }

    private fun startMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment()).commit()
    }

    override fun onDataPass(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, fragment).addToBackStack(null).commit()
    }

    override fun onDataDelete() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}