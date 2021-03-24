package dev.iamspathan.worldflagquiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import dev.iamspathan.worldflagquiz.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityQuizQuestionsBinding

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mScore = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserName = intent?.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestion()
        setQuestion()
        binding.apply {
            tvOptionOne.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionTwo.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionThree.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionFour.setOnClickListener(this@QuizQuestionsActivity)
            buttonSubmit.setOnClickListener(this@QuizQuestionsActivity)
        }
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()
        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.buttonSubmit.text = "FINISH"
        } else {
            binding.buttonSubmit.text = "SUBMIT"
        }
        binding.apply {
            progressBar.progress = mCurrentPosition
            progressText.text = "$mCurrentPosition / ${progressBar.max}"
            tvQuestion.text = question.question
            ivImage.setImageResource(question.image)
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour
        }
    }

    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()

        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.def_bg)
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_option_one -> selectedOption(binding.tvOptionOne, 1)
            R.id.tv_option_two -> selectedOption(binding.tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOption(binding.tvOptionThree, 3)
            R.id.tv_option_four -> selectedOption(binding.tvOptionFour, 4)
            R.id.button_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    nextQuestion()
                } else {
                    val question = mQuestionsList!![mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border)
                    } else {
                        mScore++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border)
                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.buttonSubmit.text = "FINISH"
                    } else {
                        binding.buttonSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }

        }
    }

    private fun selectedOption(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)
    }

    private fun answerView(answer: Int, drawable: Int) {

        when (answer) {
            1 -> binding.tvOptionOne.background =
                ContextCompat.getDrawable(this, drawable)

            2 -> binding.tvOptionTwo.background =
                ContextCompat.getDrawable(this, drawable)

            3 -> binding.tvOptionThree.background =
                ContextCompat.getDrawable(this, drawable)

            4 -> binding.tvOptionFour.background =
                ContextCompat.getDrawable(this, drawable)
        }
    }

    private fun nextQuestion() {
        if (mCurrentPosition <= mQuestionsList!!.size) {
            setQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
            intent.putExtra(Constants.USER_NAME, mUserName)
            intent.putExtra(Constants.TOTAL_SCORE, mScore)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Are you sure you want to exit ?")
            .setPositiveButton("Yes") { _, _ ->
                this.finish()
            }
            .setNegativeButton("No") { dialogue, _ ->
                dialogue.dismiss()
            }
            .create()

        alertDialog.show()
    }
}