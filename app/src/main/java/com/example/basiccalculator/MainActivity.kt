package com.example.basiccalculator

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    //DISPLAY
    private lateinit var txtExpression: TextView
    private lateinit var txtResult: TextView

    //OPERANDS
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnZero: Button
    private lateinit var btnDot: Button

    //OPERATORS
    private lateinit var btnPlus: Button
    private lateinit var btnMinus: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnOpen: Button
    private lateinit var btnClose: Button
    private lateinit var btnPercentage: Button
    private lateinit var btnPower: Button
    private lateinit var btnRoot: Button

    //OPERATION
    private lateinit var btnEquals: Button
    private lateinit var btnBackspace: ImageButton
    private lateinit var btnAllClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setListeners()

    }

    //INITIALIZE VIEWS
    private fun initViews() {
        //DISPLAY
        txtExpression = findViewById(R.id.txtExpression)
        txtResult = findViewById(R.id.txtResult)

        //OPERANDS
        btnOne = findViewById(R.id.btnOne)
        btnTwo = findViewById(R.id.btnTwo)
        btnThree = findViewById(R.id.btnThree)
        btnFour = findViewById(R.id.btnFour)
        btnFive = findViewById(R.id.btnFive)
        btnSix = findViewById(R.id.btnSix)
        btnSeven = findViewById(R.id.btnSeven)
        btnEight = findViewById(R.id.btnEight)
        btnNine = findViewById(R.id.btnNine)
        btnZero = findViewById(R.id.btnZero)
        btnDot = findViewById(R.id.btnDot)

        //OPERATORS
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        btnOpen = findViewById(R.id.btnOpen)
        btnClose = findViewById(R.id.btnClose)
        btnPercentage = findViewById(R.id.btnPercentage)
        btnPower = findViewById(R.id.btnPower)
        btnRoot = findViewById(R.id.btnRoot)

        //OPERATION
        btnEquals = findViewById(R.id.btnEquals)
        btnBackspace = findViewById(R.id.btnBackspace)
        btnAllClear = findViewById(R.id.btnAllClear)
    }

    private fun setListeners() {

        var expression = ""

        //INITIALIZE
        val operandList = listOf(btnOne, btnTwo, btnThree, btnFour,
            btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero)

        val operatorList = listOf(btnPlus, btnMultiply, btnDivide, btnPercentage, btnPower)

        //FLAGS
        var isOperand = false
        var isDot = false
        var isClose = false
        var isEqual = false

        for (item in operandList) {
            item.setOnClickListener {

                if(isEqual) {
                    expression = ""
                    txtExpression.text = expression
                    txtResult.text = ""
                }

                if (isClose) {
                    expression += btnMultiply.text
                    isClose = false
                }
                expression += item.text
                txtExpression.text = expression
                isOperand = true
                isEqual = false
            }
        }

        for (item in operatorList) {
            item.setOnClickListener {

                if(isEqual) {
                    expression = ""
                    expression += txtResult.text
                    txtExpression.text = expression
                    txtResult.text = ""
                }

                if (expression == "") Toast.makeText(this, "Please Enter a Number",
                    Toast.LENGTH_SHORT).show()

                else if (expression[expression.length - 1] == '√') Toast.makeText(this,
                    "Please Enter a Number", Toast.LENGTH_SHORT).show()

                else if (expression[expression.length - 1] == '+' || expression[expression.length - 1] == 'x' ||
                    expression[expression.length - 1] == '/' || expression[expression.length - 1] == '%' ||
                    expression[expression.length - 1] == '^' || expression[expression.length - 1] == '-') {

                    expression = expression.substring(0, expression.length - 1)
                    expression += item.text
                    txtExpression.text = expression

                } else {
                    expression += item.text
                    txtExpression.text = expression
                }
                isOperand = false
                isDot = false
                isClose = false
                isEqual = false
            }
        }

        //MINUS & NEGATIVE OPERATOR
        btnMinus.setOnClickListener {

            if(isEqual) {
                expression = ""
                expression += txtResult.text
                txtExpression.text = expression
                txtResult.text = ""
            }

            if (expression == "") {
                expression += btnMinus.text
                txtExpression.text = expression

            } else if (expression[expression.length - 1] == '+' || expression[expression.length - 1] == '-') {
                expression = expression.substring(0, expression.length - 1)
                expression += btnMinus.text
                txtExpression.text = expression

            } else {
                expression += btnMinus.text
                txtExpression.text = expression
            }
            isOperand = false
            isDot = false
            isClose = false
            isEqual = false
        }

        //ROOT OPERATOR
        btnRoot.setOnClickListener {
            if (isOperand) {
                expression += btnMultiply.text
                isOperand = false
            }
            expression += btnRoot.text
            txtExpression.text = expression
            isEqual = false
        }

        //OPEN PARENTHESIS
        btnOpen.setOnClickListener {
            isClose = false
            if (isOperand) {
                expression += btnMultiply.text
                isOperand = false
            }
            expression += btnOpen.text
            txtExpression.text = expression
            isEqual = false
        }

        //CLOSE PARENTHESIS
        btnClose.setOnClickListener {
            expression += btnClose.text
            txtExpression.text = expression
            isClose = true
            isEqual = false
        }

        //ALL CLEAR
        btnAllClear.setOnClickListener {
            isOperand = false
            isDot = false
            isClose = false
            isEqual = false
            txtExpression.text = ""
            txtResult.text = ""
            expression = ""
        }

        //BACKSPACE
        btnBackspace.setOnClickListener {
            if (expression.isNotEmpty()) {
                if (expression[expression.length - 1] == ')') isClose = false
                else if (expression[expression.length - 1] == '.') isDot = false
                else if (expression[expression.length - 1] == 'x' ||
                         expression[expression.length - 1] == '/' ||
                         expression[expression.length - 1] == '+' ||
                         expression[expression.length - 1] == '-') {
                    when {
                        expression[expression.length - 2] == ')' -> isClose = true
                        expression[expression.length - 2] == '1' -> isOperand = true
                        expression[expression.length - 2] == '2' -> isOperand = true
                        expression[expression.length - 2] == '3' -> isOperand = true
                        expression[expression.length - 2] == '4' -> isOperand = true
                        expression[expression.length - 2] == '5' -> isOperand = true
                        expression[expression.length - 2] == '6' -> isOperand = true
                        expression[expression.length - 2] == '7' -> isOperand = true
                        expression[expression.length - 2] == '8' -> isOperand = true
                        expression[expression.length - 2] == '9' -> isOperand = true
                        expression[expression.length - 2] == '0' -> isOperand = true
                    }
                }

                expression = expression.substring(0, expression.length - 1)
                txtExpression.text = expression

            } else {
                isOperand = false
                isDot = false
                isClose = false
            }
            txtResult.text = ""
            isEqual = false
        }

        //DOT OPERATOR
        btnDot.setOnClickListener {
            if (expression.isNotEmpty()) {
                if (!isDot) {
                    if (expression[expression.length - 1] == '.') {
                        Toast.makeText(this, "Enter Operand", Toast.LENGTH_SHORT).show()
                    } else {
                        expression += btnDot.text
                        txtExpression.text = expression
                        isDot = true
                    }
                }
            } else {
                expression += btnDot.text
                txtExpression.text = expression
                isDot = true
            }
            isEqual = false
        }

        //EQUAL OPERATOR
        btnEquals.setOnClickListener {
            evaluate()
            isEqual = true
        }

    }

    private fun evaluate() {

        try {
            val expression = "(${txtExpression.text})"
            val operands = Stack<Double>()
            val operators = Stack<Char>()
            var temp = ""
            var isNegative = false

            for (i in expression.indices) {

                if (expression[i] == '1' || expression[i] == '2' || expression[i] == '3' || expression[i] == '4' ||
                    expression[i] == '5' || expression[i] == '6' || expression[i] == '7' || expression[i] == '8' ||
                    expression[i] == '9' || expression[i] == '0' || expression[i] == '0' || expression[i] == '.') {

                    if(isNegative) {
                        temp += "-$temp"
                        isNegative = false
                    }
                    temp += expression[i]

                } else if (expression[i] == '(') {
                    if(isNegative) {
                        operands.push(-1.0)
                        operators.push('x')
                        isNegative = false
                    }
                    operators.push(expression[i])
                }

                else if (expression[i] == ')') {

                    if (temp.isNotEmpty()) {
                        operands.push(temp.toDouble())
                        temp = ""
                    }

                    while (operators.peek() != '(') {
                        val op = operators.pop()
                        if (op == '√') {
                            val value2 = operands.pop()
                            val value1 = 0.5
                            val ans = calculation(value1, value2, op)
                            operands.push(ans)
                        } else {
                            val value2 = operands.pop()
                            val value1 = operands.pop()
                            val ans = calculation(value1, value2, op)
                            operands.push(ans)
                        }
                    }
                    operators.pop()

                } else if (expression[i] == '+' || expression[i] == '-' || expression[i] == 'x' ||
                    expression[i] == '/' || expression[i] == '%' || expression[i] == '^' ||
                    expression[i] == '√') {

                    if (temp.isNotEmpty()) {
                        operands.push(temp.toDouble())
                        temp = ""
                    }

                    if ((expression[i] == '-' && expression[i - 1] == 'x') ||
                        (expression[i] == '-' && expression[i - 1] == '/') ||
                        (expression[i] == '-' && expression[i - 1] == '%') ||
                        (expression[i] == '-' && expression[i - 1] == '^') ||
                        (expression[i] == '-' && expression[i - 1] == '(') ) {

                        if ((expression[i] == '-' && expression[i - 1] == '(')) {
                            operands.push(-1.0)
                            operators.push('x')
                        }else isNegative = true

                    } else {
                        while (operators.size > 0 && operators.peek() != '(' && precedence(
                                expression[i]) <= precedence(operators.peek())) {

                            val op = operators.pop()
                            if (op == '√') {
                                val value2 = operands.pop()
                                val value1 = 0.5
                                val ans = calculation(value1, value2, op)
                                operands.push(ans)
                            } else {
                                val value2 = operands.pop()
                                val value1 = operands.pop()
                                val ans = calculation(value1, value2, op)
                                operands.push(ans)
                            }
                        }
                        operators.push(expression[i])
                    }
                }
            }

            while (operators.size != 0) {
                val op = operators.pop()
                if (op == '√') {
                    val value2 = operands.pop()
                    val value1 = 0.5
                    val ans = calculation(value1, value2, op)
                    operands.push(ans)
                } else {
                    val value2 = operands.pop()
                    val value1 = operands.pop()
                    val ans = calculation(value1, value2, op)
                    operands.push(ans)
                }
            }

            txtResult.text = formatResult(operands.peek())

        } catch (e: Exception) {
            Toast.makeText(this, "BAD EXPRESSION", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculation(value1: Double, value2: Double, operator: Char): Double =
        when (operator) {
            '+' -> value1 + value2
            '-' -> value1 - value2
            'x' -> value1 * value2
            '/' -> value1 / value2
            '%' -> value1 * value2 / 100
            '^' -> value1.pow(value2)
            else -> value2.pow(value1)
        }

    private fun precedence(operator: Char): Int =
        when (operator) {
            '+' -> 1
            '-' -> 1
            'x' -> 2
            '/' -> 2
            '%' -> 2
            '^' -> 3
            else -> 3
        }

    private fun formatResult(number: Double): String = DecimalFormat("#,###.##########").format(number)
}
