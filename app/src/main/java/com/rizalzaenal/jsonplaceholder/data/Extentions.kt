package com.rizalzaenal.jsonplaceholder.data

fun Int?.replaceNull() : Int {
    return this ?: 0
}

fun String?.replaceNull() : String {
    return this ?: ""
}