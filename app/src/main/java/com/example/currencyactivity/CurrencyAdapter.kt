package com.example.currencyactivity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.ArrayList

class CurrencyAdapter(context: Context, var rates: ArrayList<Rate>) : ArrayAdapter<Rate>(context, 0, rates) {
    /**
     * Converts currency code to country flag emoji, this works by using the first 2 letters of
     * the currency code
     */
    private fun localeToEmoji(countryCode: String): String? {
        if (countryCode.length < 2) {
            return ""
        }
        Log.d("emoji", countryCode)
        val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(
            Character.toChars(
                secondLetter
            )
        )
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.currency_layout, parent, false)
        val rate = getItem(position)
        if (rate != null) {
            view.findViewById<TextView>(R.id.currency_name).text = rate.name
            view.findViewById<TextView>(R.id.currency_flag).text = localeToEmoji(rate.name)
            view.findViewById<TextView>(R.id.currency_value).text = String.format("%.2f", rate.spotRate)
        }
        return view
    }
}