package com.gutotech.calculator

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var operation = "+"
    private var firstNumber = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNumberButtonClick(view: View) {
        val button = view as Button
        editText.setText(editText.text.toString() + button.text)
    }

    fun onOperationButtonClick(view: View) {
        val button = view as Button
        operation = button.text.toString()
        firstNumber = editText.text.toString()
        editText.text.clear()
    }

    fun onPercentButtonClick(view: View) {
        editText.setText((editText.text.toString().toDouble() / 100).toString())
    }

    fun onDotButtonClick(view: View) {
        if (!editText.text.toString().contains(".")) {
            editText.setText("${editText.text}.")
        }
    }

    fun onPlusMinusButtonClick(view: View) {
        if (editText.text.toString().startsWith("-")) {
            editText.setText(
                editText.text.toString().substring(1, editText.text.toString().length)
            )
        } else {
            editText.setText("-${editText.text}")
        }
    }

    fun onEqualButtonClick(view: View) {
        val newNumber = editText.text.toString()
        var result = 0.0

        when (operation) {
            "+" -> result = firstNumber.toDouble() + newNumber.toDouble()
            "-" -> result = firstNumber.toDouble() - newNumber.toDouble()
            "x" -> result = firstNumber.toDouble() * newNumber.toDouble()
            "÷" -> {
                if (newNumber == "0" || newNumber.isEmpty()) {
                    val toast = Toast.makeText(this, "Can't divide by 0", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 20)
                    toast.show()
                } else {
                    result = firstNumber.toDouble() / newNumber.toDouble()
                }
            }
        }

        editText.setText(result.toString())
    }

    fun onClearButtonClick(view: View) {
        if (view is Button) { // AC
            editText.setText("")
        } else if (editText.text.toString().isNotEmpty()) {
            editText.setText(
                editText.text.toString().substring(0, editText.text.toString().length - 1)
            )
        }
    }
}