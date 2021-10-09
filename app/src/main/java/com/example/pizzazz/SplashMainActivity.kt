package com.example.pizzazz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pizzazz.databinding.ActivitySplashMainBinding

class SplashMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun init() {

        intent = Intent(this, SplashMainActivity::class.java)
        startActivity(intent)
        finish()
    }
}