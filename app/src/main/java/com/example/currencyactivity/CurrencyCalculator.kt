package com.example.currencyactivity

class CurrencyCalculator {
    fun scaleRatesUp(baseVal: Double, rates: ArrayList<Rate>): ArrayList<Rate> {
        val newRates = ArrayList<Rate>()
        for (rate in rates) {
            newRates.add(Rate(rate.name, rate.spotRate * baseVal))
        }
        return newRates
    }
}