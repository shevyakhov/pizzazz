package com.example.pizzazz.activities

import viewModel.StateViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.pizzazz.R
import com.example.pizzazz.databinding.ActivityMainBinding
import fragments.SplashFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var stateVM: StateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashScreen()
        initVm()
    }

    private fun splashScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, SplashFragment()).commit()
    }

    private fun openFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, frag).addToBackStack(null).commit()
    }

    private fun deleteAllFragments(frag: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun initVm() {
        stateVM = ViewModelProvider(this)[StateViewModel::class.java]
        /*check of ViewModel's changes */
        stateVM.addFragmentLive.observe(this, { frag ->
            openFragment(frag)
        })
        stateVM.deleteFragmentLive.observe(this, { frag ->
            deleteAllFragments(frag)
        })
    }

}