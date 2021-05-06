package com.example.currencyactivity

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection


class JsonReader {
    fun streamToString(input: InputStreamReader): String? {
        var bufferedReader: BufferedReader? = null
        return try {
            bufferedReader = BufferedReader(input)
            val stringBuffer = StringBuffer()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            return stringBuffer.toString()
        } catch (ex: Exception) {
            Log.e("App", "yourDataTask", ex)
            null
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}