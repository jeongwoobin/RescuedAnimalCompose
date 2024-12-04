package com.example.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.orhanobut.logger.Logger
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREAN)
    val myDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)

    fun convertMyDateFormat(date: String?): String? {
        val parse = date?.let { dateFormat.parse(it) }
        return parse?.let { myDateFormat.format(it) }
    }

    fun snackBarContent(isError: Boolean = false, content: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("isError", isError)
        jsonObject.put("content", content)

        return jsonObject.toString()
    }

    fun intentActionDial(context: Context, tel: String) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel"))
        context.startActivity(callIntent)
    }

    fun intentActionView(context: Context, location: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/p/search/$location"))
        val chooser = Intent.createChooser(intent, "")

        try {
            context.startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Logger.e("FAIL Open")
        }
    }
}