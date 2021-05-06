package com.example.currencyactivity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CurrencyActivity : AppCompatActivity(), CurrencyPresenter.CurrencyView {
    val currencyDAO: CurrencyDAO = FixerCurrency()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)


        val textView = findViewById<EditText>(R.id.number)

        // setup listener on our EditText so we can update the rates after they're done typing
        textView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                displayData(currencyDAO)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        val spinner = findViewById<Spinner>(R.id.rate_names)
        // setup listener on
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {/* Do nothing */}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                displayData(currencyDAO)
            }
        }
        CurrencyPresenter(this).getCurrencyBases(currencyDAO)
    }

    override fun updateCurrencyBases(bases: ArrayList<String>) {
        runOnUiThread {
            val spinner = findViewById<Spinner>(R.id.rate_names)
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                bases
            )
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    fun displayData(currencyDAO: CurrencyDAO) {
        runOnUiThread {
            val spinner = findViewById<Spinner>(R.id.rate_names)
            val currentBase = spinner.selectedItem as String
            val currencyAmount = findViewById<EditText>(R.id.number).text.toString().toDoubleOrNull() ?: 1.0

            CurrencyPresenter(this).getRates(currencyDAO, currencyAmount, currentBase)
        }
    }

    override fun updateRateList(data: ArrayList<Rate>) {
        runOnUiThread {
            val listView = findViewById<ListView>(R.id.rate_list)

            listView.adapter = CurrencyAdapter(this, data)
        }
    }
}