package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var board = Array(9) { "" }
    private var currentPlayer = 0
    private var isGameOver = false

    private var playerXWinsCount = 0
    private var playerYWinsCount = 0

    private val winnerPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerXTextView.text =
            getString(R.string.win_counter, "X", playerXWinsCount)
        playerYTextView.text =
            getString(R.string.win_counter, "Y", playerXWinsCount)
    }

    fun onButtonSelected(view: View) {
        if (isGameOver) {
            return
        }

        val buttonSelected = view as Button
        val cell = Integer.parseInt(buttonSelected.tag.toString()) - 1
        playGame(cell, buttonSelected)
    }

    fun playAgain(view: View) {
        restart()
    }

    private fun playGame(cell: Int, button: Button) {
        if (currentPlayer == 0) {
            button.text = "X"
            board[cell] = "X"
        } else {
            button.text = "O"
            board[cell] = "O"
        }

        button.isEnabled = false

        checkWinner()

        currentPlayer = (currentPlayer + 1) % 2
    }

    private fun checkWinner() {
        for (winnerPosition in winnerPositions) {
            if (board[winnerPosition[0]] == board[winnerPosition[1]] &&
                board[winnerPosition[1]] == board[winnerPosition[2]] &&
                board[winnerPosition[0]].isNotEmpty()
            ) { // someone has won
                isGameOver = true

                if (board[winnerPosition[0]] == "X") {
                    Toast.makeText(this, "Player X has won!", Toast.LENGTH_LONG).show()
                    playerXTextView.text =
                        getString(R.string.win_counter, "X", ++playerXWinsCount)
                } else {
                    Toast.makeText(this, "Player Y has won!", Toast.LENGTH_LONG).show()
                    playerYTextView.text =
                        getString(R.string.win_counter, "Y", ++playerYWinsCount)
                }

                break
            }
        }
    }

    private fun restart() {
        currentPlayer = 0
        board.fill("")

        tableLayout.children.forEach { view ->
            val tableRow = view as TableRow

            tableRow.children.forEach { view2 ->
                val button = view2 as Button
                button.text = ""
                button.isEnabled = true
            }
        }

        isGameOver = false
    }
}