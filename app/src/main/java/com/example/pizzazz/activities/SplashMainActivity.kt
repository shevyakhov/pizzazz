package com.example.pizzazz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.pizzazz.databinding.ActivitySplashMainBinding

class SplashMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        animateSplashScreen()
    }
    private fun animateSplashScreen() {
        binding.splashLogo.animate().alpha(0.0f).duration = 0
        Handler(Looper.getMainLooper()).postDelayed({
            binding.splashLogo.animate().alpha(1.0f).duration = 500
        }, 300)
        Handler(Looper.getMainLooper()).postDelayed({
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, 1500)
        /* TODO Get rid of delay when actual work needed*/
    }

}