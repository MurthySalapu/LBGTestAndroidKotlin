package com.lbg.lbgtest.enums

/**
 * Enum for Search Type
 */
enum class SearchType constructor(val key: String) {
    METHOD("method"),
    TRACK("track"),
    ALBUM("album"),
    ARTIST("artist"),
    API_KEY("api_key"),
    FORMAT("format")
}
