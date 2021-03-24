package dev.iamspathan.worldflagquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import dev.iamspathan.worldflagquiz.databinding.ActivityMainBinding
import dev.iamspathan.worldflagquiz.databinding.ActivityQuizQuestionsBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonStart.setOnClickListener {
            if (binding.etName.text.toString().isNullOrBlank()) {
                Toast.makeText(this, "Pleassye Enter Your Name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                val username = binding.etName.text.toString()
                intent.putExtra(Constants.USER_NAME, username)
                startActivity(intent)
                finish()
            }
        }
    }
}