package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.lang.StringBuilder

enum class WhichOperandNow{
    FIRST,
    SECOND
}

class MainActivity : AppCompatActivity() {

    private var firstOperand: String = "0"
    private var firstOperandHaveDot: Boolean = false
    private var secondOperand: String = ""
    private var secondOperandHaveDot: Boolean = false
    private var operator: Char? = null
    private var whichOperandNow: WhichOperandNow = WhichOperandNow.FIRST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun clickNumber(view: android.view.View) {
        val button: Button = view as Button

        addNumberToOperand(button.text)
        showDisplay()

    }

    private fun showDisplay(){
        val display = findViewById<EditText>(R.id.editDisplay)

        if (operator == null) {
            display.setText(firstOperand)
        }
        else {
            display.setText(StringBuilder().append(firstOperand).append(operator).append(secondOperand))
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun addNumberToOperand(number: CharSequence) = when (whichOperandNow){
        WhichOperandNow.FIRST -> {
            if (firstOperand.length < 8)
                addFirstOperand(number)
            else {{}}
        }
        WhichOperandNow.SECOND -> {
            if (secondOperand.length < 8)
                addSecondOperand(number)
            else {{}}
        }
    }

    private fun addFirstOperand(number: CharSequence){
        if(number == "." && firstOperandHaveDot) {
            {}
        }
        else if (firstOperand == "0" && !number.equals('0')){
            firstOperand = if (number == ".")
                "0$number"
            else
                "$number"
        }
        else {
            firstOperand = "$firstOperand$number"
        }

        if(number == "." && !firstOperandHaveDot) {
            firstOperandHaveDot = true
        }
        else {{}}
    }

    private fun addSecondOperand(number: CharSequence){
        if(number == "." && secondOperandHaveDot) {
            {}
        }
        else if ((secondOperand == "" && number == "." ) || (secondOperand == "0" && number == "0" )){
            {}
        }
        else if (secondOperand == "0" && number != ".") {
            secondOperand = "$number"
        }
        else{
            secondOperand = "$secondOperand$number"
        }

        if(number == "." && !secondOperandHaveDot && secondOperand != "") {
            secondOperandHaveDot = true
        }
        else {{}}
    }

    fun clickClear(view: android.view.View) {
        clearAll()
    }

    private fun clearAll(){
        whichOperandNow = WhichOperandNow.FIRST
        firstOperand = "0"
        secondOperand = ""
        firstOperandHaveDot = false
        secondOperandHaveDot = false
        operator = null
        showDisplay()
    }

    fun clickOperator(view: android.view.View) {
        val button: Button = view as Button
        operator = button.text.toString()[0]
        whichOperandNow = WhichOperandNow.SECOND
        showDisplay()
    }

    private fun undo() {
        when (whichOperandNow){
            WhichOperandNow.FIRST -> {
                if (firstOperand.isNotEmpty()) {
                    if (firstOperand[firstOperand.length - 1] == '.')
                        firstOperandHaveDot = false
                    firstOperand = firstOperand.substring(0, firstOperand.length - 1)
                }
            }
            WhichOperandNow.SECOND -> {
                if (secondOperand.isNotEmpty()) {
                    if (secondOperand[secondOperand.length - 1] == '.')
                        secondOperandHaveDot = false
                    secondOperand = secondOperand.substring(0, secondOperand.length - 1)
                }
                else
                    if (operator != null) {
                        operator = null
                        whichOperandNow = WhichOperandNow.FIRST
                    }
            }
        }
        showDisplay()
    }

    fun goUndo(view: android.view.View) {
        undo()
    }

    fun doMath(view: android.view.View){
        if (operator != null && secondOperand.isNotEmpty()) {
            val firstNumber: Double = firstOperand.toDouble()
            val secondNumber: Double = secondOperand.toDouble()
            val op = operator!!

            giveResult(firstNumber, secondNumber, op)
        }
        else{{}}
    }

    private fun giveResult(firstNumber:Double, secondNumber: Double, operator: Char) {
        clearAll()

        when (operator){
            '+' ->{
                firstOperand = (firstNumber+secondNumber).toString()
            }
            '-' ->{
                firstOperand = (firstNumber-secondNumber).toString()
            }
            '×' ->{
                firstOperand = (firstNumber*secondNumber).toString()
            }
            '÷' ->{
                firstOperand = (firstNumber/secondNumber).toString()
            }
        }

        if (firstOperand.contains(".")){
            firstOperandHaveDot = true
        }

        if(firstOperand.endsWith(".0")){
            undo()
            undo()
        }

        showDisplay()
    }
}