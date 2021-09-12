package com.example.canvassample

import android.os.Bundle
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    private lateinit var graphView: GraphView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        presenter.buildGraph()
    }

    companion object {
        const val TIME_INTERVAL = 2L
    }

    override fun updatePointList(list: ArrayList<Int>) {
        graphView.updatePointList(list)
    }

    override fun initGraph() {
        graphView = findViewById(R.id.graph)
        graphView.setStep(40f)
    }
}