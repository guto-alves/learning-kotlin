package com.example.braintrainer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.security.SecureRandom

class GameActivity : AppCompatActivity() {
    private var rightAnswer = 0
    private var numberOfQuestions = 0
    private var score = 0
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        play(playAgainButton)
    }

    fun play(view: View) {
        playAgainButton.visibility = View.GONE
        score = 0
        numberOfQuestions = 0
        resultTextView.text = ""
        isGameOver = false
        scoreTextView.text = "0/0"

        generateNewQuestion()

        object : CountDownTimer(30500, 1000) {
            override fun onTick(currentMillis: Long) {
                val seconds = currentMillis / 1000
                timerTextView.text = "${seconds}s"
            }

            override fun onFinish() {
                isGameOver = true
                resultTextView.text = "Done!"
                playAgainButton.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun generateNewQuestion() {
        val number1 = random.nextInt(21)
        val number2 = random.nextInt(21)

        rightAnswer = number1 + number2

        val answers = ArrayList<Int>()
        answers.add(rightAnswer)

        while (answers.size < 4) {
            val answerGenerated = random.nextInt(41)

            if (!answers.contains(answerGenerated)) {
                answers.add(answerGenerated)
            }
        }

        questionTextView.text = "$number1 + $number2 ?"

        button.text = answers[0].toString()
        button1.text = answers[1].toString()
        button2.text = answers[2].toString()
        button3.text = answers[3].toString()

        numberOfQuestions++
    }

    fun chooseAnswer(view: View) {
        if (isGameOver) {
            return
        }

        val button = view as Button

        if (button.text.toString().toInt() == rightAnswer) {
            score++
            resultTextView.text = "Correct!"
        } else {
            resultTextView.text = "Wrong :("
        }

        scoreTextView.text = "${score}/$numberOfQuestions"

        generateNewQuestion()
    }

    companion object {
        private val random = SecureRandom()
    }
}