package com.thepascal.greenflag

import androidx.core.util.PatternsCompat


fun isEmailValid(email: String): Boolean = !email.isBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

fun countGroup(value: String): Int {
    val lowercaseRange = 'a'..'z'
    val uppercaseRange = 'A'..'Z'
    val numberRange = '0'..'9'

    var count = 0

    if (value.any { it in lowercaseRange }) {
        count++
    }
    if (value.any { it in uppercaseRange }) {
        count++
    }
    if (value.any { it in numberRange }) {
        count++
    }

    return count
}

fun isPasswordValid(password: String): Boolean = (countGroup(password) >= 3 && password.length >= 8)

fun isUsernameValid(username: String): Boolean  = (countGroup(username) >= 2 && username.length >= 4)