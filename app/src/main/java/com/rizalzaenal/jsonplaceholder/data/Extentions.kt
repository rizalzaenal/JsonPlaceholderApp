package com.rizalzaenal.jsonplaceholder.data

fun Int?.replaceNull() : Int {
    return this ?: 0
}

fun Boolean?.replaceNull() : Boolean {
    return this ?: false
}

fun String?.replaceNull() : String {
    return this ?: ""
}

fun Double?.replaceNull() : Double {
    return this ?: 0.0
}