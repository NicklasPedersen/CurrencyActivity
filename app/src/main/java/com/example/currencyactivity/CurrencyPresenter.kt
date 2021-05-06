package com.example.currencyactivity


class CurrencyPresenter(private val currencyView: CurrencyView) : CurrencyDAO.Presenter {
    fun getCurrencyBases(currencyDAO: CurrencyDAO) {
        currencyDAO.getAllAvailableRateNames(this)
    }

    fun getRates(currencyDAO: CurrencyDAO, currencyAmount: Double, currencyBase: String) {
        currencyDAO.getRates(this, currencyBase, currencyAmount)
    }

    interface CurrencyView {
        fun updateCurrencyBases(bases: ArrayList<String>)
        fun updateRateList(data: ArrayList<Rate>)
    }

    override fun onReceiveCurrencyBases(bases: ArrayList<String>) {
        currencyView.updateCurrencyBases(bases)
    }

    override fun onReceiveRateList(data: ArrayList<Rate>) {
        currencyView.updateRateList(data)
    }
}