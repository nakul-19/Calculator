package com.calculator

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel
    lateinit var exp: TextView
    lateinit var result: TextView
    lateinit var scrollView: HorizontalScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        exp = findViewById(R.id.expression)
        result = findViewById(R.id.result)
        scrollView = findViewById(R.id.scrollView)

        findViewById<TextView>(R.id.n0).setOnClickListener { clickedN(0) }
        findViewById<TextView>(R.id.n1).setOnClickListener { clickedN(1) }
        findViewById<TextView>(R.id.n2).setOnClickListener { clickedN(2) }
        findViewById<TextView>(R.id.n3).setOnClickListener { clickedN(3) }
        findViewById<TextView>(R.id.n4).setOnClickListener { clickedN(4) }
        findViewById<TextView>(R.id.n5).setOnClickListener { clickedN(5) }
        findViewById<TextView>(R.id.n6).setOnClickListener { clickedN(6) }
        findViewById<TextView>(R.id.n7).setOnClickListener { clickedN(7) }
        findViewById<TextView>(R.id.n8).setOnClickListener { clickedN(8) }
        findViewById<TextView>(R.id.n9).setOnClickListener { clickedN(9) }
        findViewById<TextView>(R.id.dot).setOnClickListener { clickedN(-1) }

        findViewById<TextView>(R.id.bOpen).setOnClickListener { clickedB('o') }
        findViewById<TextView>(R.id.bClose).setOnClickListener { clickedB('c') }

        findViewById<TextView>(R.id.plus).setOnClickListener { clickedO('+') }
        findViewById<TextView>(R.id.minus).setOnClickListener { clickedO('-') }
        findViewById<TextView>(R.id.divide).setOnClickListener { clickedO('/') }
        findViewById<TextView>(R.id.multiply).setOnClickListener { clickedO('*') }

        findViewById<ImageView>(R.id.delete).setOnClickListener { clickedA(it) }
        findViewById<TextView>(R.id.clear).setOnClickListener { clickedA(it) }
        findViewById<TextView>(R.id.equal).setOnClickListener { clickedA(it) }

        viewModel.exp.observe(this, {
            exp.text = it
        })

        viewModel.answer.observe(this, {
            result.text = it
        })
    }

    private fun clickedA(it: View) {
        when (it.id) {
            R.id.delete -> viewModel.delete()
            R.id.equal -> viewModel.equate()
            R.id.clear -> viewModel.reset()
        }
        scrollView.postDelayed({
            kotlin.run {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }, 100L)
    }

    private fun clickedO(o: Char) {
        viewModel.addOperator(o)
        scrollView.postDelayed({
            kotlin.run {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }, 100L)
    }

    private fun clickedB(c: Char) {
        if (c == 'o')
            viewModel.bOpen()
        else
            viewModel.bClose()
        scrollView.postDelayed({
            kotlin.run {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }, 100L)
    }

    private fun clickedN(n: Int) {
        if (n != -1)
            viewModel.addNumber(n.toString())
        else
            viewModel.addNumber(".")
        scrollView.postDelayed({
            kotlin.run {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }, 100L)
    }

}