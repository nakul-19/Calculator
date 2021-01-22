package com.calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class MyViewModel : ViewModel() {

    val exp = MutableLiveData<String>()
    val answer = MutableLiveData<String>()

    init {
        exp.postValue("0")
        answer.postValue("0")
    }

    fun delete() {
        val string = exp.value!!
        if (string.length == 1)
            reset()
        else {
            exp.postValue(string.substring(0, string.length - 1))
            reevaluate()
        }
    }

    private fun reevaluate() {
        val text = getFormatted()
        Log.d("number", text)
        val expression = ExpressionBuilder(text).build()
        try {
            val result = expression.evaluate()
            if (Math.floor(result) == result)
                answer.value = result.toString()
            else
                answer.value = result.toString()
        } catch (e: Error) {
            answer.value = "Invalid"
        }
    }

    private fun getFormatted(): String {
        var string = exp.value!!
        Log.d("number", string)
        if (string[string.length - 1] == '.')
            string = string.substring(0, string.length - 1)
        var countO = 0
        var countC = 0
        for (i in string) {
            if (i == '(')
                countO++
            if (i == ')')
                countC++
        }
        if (countC == countO)
            return string
        for (i in 1..countO - countC)
            string += ")"
        return string
    }

    fun equate() {
        exp.postValue(answer.value)
    }

    fun reset() {
        exp.postValue("0")
        answer.postValue("0")
    }

    fun addOperator(o: Char) {
        var string = exp.value!!
        if (string[string.length - 1] == '.')
            string = string.substring(0, string.length - 1)
        if (string[string.length - 1] == '(')
            return
        if (!string[string.length - 1].isDigit())
            string = string.substring(0, string.length - 1)
        string += o.toString()
        exp.value = string
    }

    fun bOpen() {
        var string = exp.value!!
        string += "("
        exp.value = string
    }

    fun bClose() {
        var string = exp.value!!
        var countO = 0
        var countC = 0
        for (i in string) {
            if (i == '(')
                countO++
            if (i == ')')
                countC++
        }
        if (countC < countO) {
            string += ")"
            exp.value = string
        }
    }

    fun addNumber(n: String) {
        var string = exp.value!!
        if (string.trim() == "0")
            string = ""
        string += n
        exp.value = string
        Log.d("number", exp.value!!)
        reevaluate()
    }
}