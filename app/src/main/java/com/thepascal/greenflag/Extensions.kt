package com.thepascal.greenflag

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun TextView.displayErrorIfAny(errorInt: Int) {
    if (errorInt == 0) {
        remove()
    } else {
        text = context.getString(errorInt)
        unveil()
    }
}

fun View.unveil() {
    visibility = View.VISIBLE
}

fun View.remove() {
    visibility = View.GONE
}

fun TextInputLayout.setDrawableRight(drawable: Int) {
    editText?.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    //editText?.compoundDrawablePadding = 100
}

fun TextInputLayout.showError(errorInt: Int) {
    if (errorInt == 0) {
        error = null
        isErrorEnabled = false
    } else {
        error = context.getString(errorInt)
    }
}

/*
fun String.encrypt(password: String): String {
    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = password.toCharArray()

    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

    val encryptedValue = cipher.doFinal(password.toByteArray())

    return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
}

fun String.decrypt(password: String): String {
    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = password.toCharArray()

    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

    val decryptedValue = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))

    return String(decryptedValue)
}*/
