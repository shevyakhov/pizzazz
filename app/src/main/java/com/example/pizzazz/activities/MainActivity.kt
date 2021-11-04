package com.example.pizzazz.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pizzazz.R
import com.example.pizzazz.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import di.PizzaViewModelFactory
import fragments.HomeFragment
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaModel
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnFragmentPass {
    private lateinit var binding: ActivityMainBinding


    @Inject lateinit var pizzaModelFactory: PizzaViewModelFactory
    private val model:PizzaModel by viewModels {pizzaModelFactory  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        PizzaApp.instance.pizzaComponent.inject(this)
        setTheme(R.style.SplashTheme)
        setContentView(binding.root)
        startMainFragment()


    }

    private fun startMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment()).commit()
    }

    override fun onFragmentPass(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, fragment).addToBackStack(null).commit()
    }

    override fun onDataDelete() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onDataPass(item: Int) {
       model.addPizza(item)
    }

    override fun onDialog(fragment: BottomSheetDialogFragment) {
        fragment.show(supportFragmentManager, "TAG")
    }

    override fun onPopBackStack() {
        supportFragmentManager.popBackStack()
    }

}
