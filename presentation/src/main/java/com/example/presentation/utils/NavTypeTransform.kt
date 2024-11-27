package com.example.presentation.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.orhanobut.logger.Logger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T {
        val result: T = json.decodeFromString(URLDecoder.decode(value, StandardCharsets.UTF_8.toString()))
        Logger.d("parseValue: $result")
        return result
    }

    override fun serializeAsValue(value: T): String {
        val result = URLEncoder.encode(json.encodeToString(value), StandardCharsets.UTF_8.toString())
        Logger.d("serializeAsValue: $result")
        return result
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}