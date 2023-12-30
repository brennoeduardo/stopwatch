package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null
    private var numberPause: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_value)
        val buttonStart: Button = findViewById(R.id.btn_start)
        val buttonEnd: Button = findViewById(R.id.btn_end)
        val buttonPause: Button = findViewById(R.id.btn_pause)
        val result: TextView = findViewById(R.id.result)

        buttonStart.setOnClickListener {
            try {
                val number = editText.text.toString().toLong()

                timer = object : CountDownTimer((number + (numberPause ?: 0)) * 60 * 1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        var seconds = millisUntilFinished / 1000
                        var minutes = seconds / 60
                        seconds %= 60
                        result.text = String.format("%02d:%02d", minutes, seconds)
                    }

                    override fun onFinish() {
                        result.text = "O tempo acabou!"
                        numberPause = null
                    }
                }

                timer?.start()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Digite um número no campo de texto!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonEnd.setOnClickListener {
            timer?.cancel()
            result.text = null
        }

        buttonPause.setOnClickListener {
            timer?.cancel()
            println(result.text)
            numberPause = result.text.toString().toLongOrNull()
            println("Pausado em: $numberPause")
        }
    }
}

