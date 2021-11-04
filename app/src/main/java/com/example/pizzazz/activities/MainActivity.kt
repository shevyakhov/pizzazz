package com.example.pizzazz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.pizzazz.R
import com.example.pizzazz.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.DetailsFragment
import fragments.HomeFragment
import io.reactivex.rxjava3.core.Single
import pizza_logic.OnFragmentPass
import pizza_logic.PizzaApi
import pizza_logic.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class MainActivity : AppCompatActivity(), OnFragmentPass {
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
