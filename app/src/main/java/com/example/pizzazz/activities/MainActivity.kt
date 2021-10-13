package com.example.pizzazz.activities

import viewModel.StateViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.pizzazz.R
import com.example.pizzazz.databinding.ActivityMainBinding
import fragments.BottomFragment
import fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var stateVM: StateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.SplashTheme)
        setContentView(binding.root)
        initVm()
        startMainFrag()
    }

    private fun startMainFrag() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, HomeFragment()).commit()
    }

    private fun openFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, frag).addToBackStack(null).commit()
    }

    private fun deleteAllFragments() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun initVm() {
        stateVM = ViewModelProvider(this)[StateViewModel::class.java]
        /*check of ViewModel's changes */
        stateVM.addFragmentLive.observe(this, { frag ->
            openFragment(frag)
        })
        stateVM.deleteFragmentLive.observe(this, {
            deleteAllFragments()
        })
    }

}