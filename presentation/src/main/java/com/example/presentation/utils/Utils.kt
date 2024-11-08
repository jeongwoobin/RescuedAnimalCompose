package com.example.presentation.utils

import org.json.JSONObject

object Utils {

    fun snackBarContent(isError: Boolean = false, content: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("isError", isError)
        jsonObject.put("content", content)

        return jsonObject.toString()
    }
}