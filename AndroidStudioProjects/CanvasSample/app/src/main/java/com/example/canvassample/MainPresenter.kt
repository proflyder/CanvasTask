package com.example.canvassample

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var isSubscribe = false
    private var list = ArrayList<Int>()
    private val compositeDisposable = CompositeDisposable()
    private val to = 500
    private val from = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initGraph()
        list.add(Random().nextInt((to - from) + from))
        viewState.updatePointList(list)
    }

    fun buildGraph() {
        if (!isSubscribe) {
            isSubscribe = true
            Observable.interval(MainActivity.TIME_INTERVAL, TimeUnit.SECONDS).repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list.add(Random().nextInt((to - from) + from))
                    viewState.updatePointList(list)
                }, {
                    it.stackTrace
                }).let(compositeDisposable::add)
        }
    }
}