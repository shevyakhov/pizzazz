package com.example.pizzazz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pizzazz.databinding.ActivityEndBinding

class EndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEndBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEndBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backToMenu.setOnClickListener {
            intent = Intent(this@EndActivity,SplashMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    over
}