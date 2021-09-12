package com.example.canvassample

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updatePointList(list: ArrayList<Int>)

    @StateStrategyType(SingleStateStrategy::class)
    fun initGraph()
}