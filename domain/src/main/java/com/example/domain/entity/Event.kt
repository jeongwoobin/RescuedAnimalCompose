package com.example.domain.entity

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


sealed class EventItem {
	data class LoginStatus(val status: Boolean): EventItem()
	data class Event(val type: String, val data: HashMap<String, Any?>?): EventItem()
	data class SnackBar(val data: HashMap<String, Any?>): EventItem()
	data class PushData(val data: Map<String, String>): EventItem()
}