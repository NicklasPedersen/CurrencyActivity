package com.example.currencyactivity

import android.os.AsyncTask
import android.os.Looper
import androidx.core.os.HandlerCompat
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FixerCurrency : CurrencyDAO {
    private var data: BaseCurrency? = null
    private val token = "<your token here>"


    private fun getNewDataFromAPI(): BaseCurrency {
        val url = URL("http://data.fixer.io/api/latest?access_key=$token")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream = BufferedInputStream(urlConnection.inputStream)
            val jsonString = JsonReader().streamToString(inputStream.reader())
            val dataFromSrc = generateCurrencyList(jsonString!!)
            data = dataFromSrc
            return dataFromSrc
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun getRates(
        presenter: CurrencyDAO.Presenter,
        base: String,
        currencyAmount: Double
    ) {
        if (data != null) {
            val currency: BaseCurrency = data!!
            val rates = scaleRatesUp(currencyAmount, convertFromBase(currency.base, base, currency.rates))
            presenter.onReceiveRateList(rates)
        } else {
            AsyncTask.execute {
                val currency = getNewDataFromAPI()
                val rates = scaleRatesUp(currencyAmount, convertFromBase(currency.base, base, currency.rates))
                presenter.onReceiveRateList(rates)
            }
        }
    }

    override fun getAllAvailableRateNames(presenter: CurrencyDAO.Presenter) {

        if (data != null) {
            val currency = data!!
            val rateNames = getAllAvailableRateNames(currency)
            presenter.onReceiveCurrencyBases(rateNames)
        } else {
            AsyncTask.execute {
                val currency = getNewDataFromAPI()
                val rateNames = getAllAvailableRateNames(currency)
                presenter.onReceiveCurrencyBases(rateNames)
            }
        }
    }
}