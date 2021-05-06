package com.example.currencyactivity

import android.content.Context
import android.util.Log
import org.json.JSONObject

interface CurrencyDAO {
    interface Presenter {
        fun onReceiveCurrencyBases(bases: ArrayList<String>)
        fun onReceiveRateList(data: ArrayList<Rate>)
    }
    fun getRates(presenter: Presenter, base: String, currencyAmount: Double)
    fun getAllAvailableRateNames(presenter: Presenter)

    fun getAllAvailableRateNames(data: BaseCurrency): ArrayList<String> {
        val names = ArrayList<String>()
        names.add(data.base)
        for (rate in data.rates) {
            names.add(rate.name)
        }
        return names
    }

    fun scaleRatesUp(currencyAmount: Double, data: ArrayList<Rate>): ArrayList<Rate> {
        val calculator = CurrencyCalculator()

        return calculator.scaleRatesUp(currencyAmount, data)
    }

    /**
     * Generates an ArrayList of Rates based on a JSON string
     */
    fun generateCurrencyList(JSON: String): BaseCurrency {
        Log.d("generate", JSON)
        val deserializedData = JSONObject(JSON)
        if (!deserializedData.getBoolean("success")) {
//            throw Exception("Not successful")
        }
        val base: String = deserializedData.getString("base")
        val rates = deserializedData.getJSONObject("rates")
        val arr = ArrayList<Rate>()
        val keys = rates.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            arr.add(Rate(key, rates.getDouble(key)))
        }
        return BaseCurrency(base, arr)
    }

    /**
     * converts from one base to another, this assumes that the previous base is normalized to 1
     */
    fun convertFromBase(previousBase: String, base: String, rates: ArrayList<Rate>): ArrayList<Rate> {
        if (base == previousBase) {
            return rates
        }

        // search for the rate in the array
        val newBase: Rate? = rates.find {
                rate -> rate.name == base
        }
        if (newBase == null) {
            throw Exception("Currency not accepted")
        }
        val newRates = ArrayList<Rate>()
        // Calculate the new value multiplier based on the old value (1) and the new value
        val newRateChange = 1 / newBase.spotRate
        // Add previous base to list
        newRates.add(Rate(previousBase, newRateChange))
        for (rate in rates) {
            // Do not add the new base to the list
            if (rate.name != newBase.name) {
                // Scale all rates by the multiplier
                newRates.add(Rate(rate.name, newRateChange * rate.spotRate))
            }
        }
        return newRates
    }
}