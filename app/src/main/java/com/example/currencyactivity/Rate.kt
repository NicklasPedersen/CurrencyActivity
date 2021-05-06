package com.example.currencyactivity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

class Rate(val name: String, val spotRate: Double) {
    /**
     * Returns a stringified version of Rate, in the format of:
     * "[name]: [spotRate]"
     * [spotRate] is formatted to 2 decimals
     */
    override fun toString(): String {
        return "${name}: ${String.format("%.2f", spotRate)}"
    }
}