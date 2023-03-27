package com.nong.flowsample

import android.animation.Animator
import android.animation.AnimatorInflater
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.nong.flowsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(owner = this)[MainViewModel::class.java]
    }

    private val shakeAnimation: Animator by lazy {
        AnimatorInflater.loadAnimator(this, R.animator.shake).apply {
            setTarget(binding.counter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()

        mainViewModel.count.observe(this) {
            binding.counter.text = "$it"
        }
    }

    private fun setOnClickListener() {
        binding.startButton.setOnClickListener {
            if (mainViewModel.isRunning.value) {
                Toast.makeText(this, "Is already running", Toast.LENGTH_SHORT).show()
                shakeAnimation.start()
            } else {
                Toast.makeText(this, "Counting start", Toast.LENGTH_SHORT).show()
                mainViewModel.startCount()
            }
        }

        binding.stopButton.setOnClickListener {
            if (!mainViewModel.isRunning.value) {
                Toast.makeText(this, "Is already stopped", Toast.LENGTH_SHORT).show()
                shakeAnimation.start()
            } else {
                Toast.makeText(this, "Counting stopped", Toast.LENGTH_SHORT).show()
                mainViewModel.stopCount()
            }
        }

        binding.resetButton.setOnClickListener {
            Toast.makeText(this, "Counter reset", Toast.LENGTH_SHORT).show()
            mainViewModel.resetCounter()
        }
    }
}