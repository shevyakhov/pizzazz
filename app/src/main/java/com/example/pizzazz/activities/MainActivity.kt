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
import pizza_logic.Pizza
import pizza_logic.PizzaEntity
import pizza_logic.PizzaModel

class MainActivity : AppCompatActivity(), OnFragmentPass{
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: PizzaModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        model = ViewModelProvider(this)[PizzaModel::class.java]
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

    override fun onPassLiveData(item: Pizza) {
        model.addPizza(item)
    }
}