package dev.iamspathan.worldflagquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.iamspathan.worldflagquiz.databinding.ActivityFinishBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding : ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(Constants.USER_NAME)
        binding.tvUsername.text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0 )
        val totalScore = intent.getIntExtra(Constants.TOTAL_SCORE,0)

        binding.tvScore.text = "Your Score is $totalScore / $totalQuestions"

        binding.buttonFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}