package com.thepascal.greenflag

import android.app.DatePickerDialog
import android.content.Context
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import kotlin.collections.HashMap

fun showDatePickerDialog(context: Context, listener: DatePickerDialog.OnDateSetListener) {
    val datePickerDialog = DatePickerDialog(
        context,
        listener,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.show()
}

fun encrypt(password: String?): HashMap<String, ByteArray> {
    val map = HashMap<String, ByteArray>()

    try {
        //Get the key
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val secretKeyEntry = keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey

        //Encrypt data
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val ivBytes: ByteArray = cipher.iv
        val encryptedBytes = cipher.doFinal(password?.toByteArray(Charsets.UTF_8))

        map["iv"] = ivBytes
        map["encrypted"] = encryptedBytes

    } catch (e: Throwable) {
        e.printStackTrace()
    }

    return map
}

fun decrypt(map: HashMap<String, ByteArray>): String? {
    var decrypted: String? = null

    try {
        //Get the key
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val secretKeyEntry = keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey

        //Extract info from map
        val encryptedBytes = map["encrypted"]
        val ivBytes = map["iv"]

        //Decrypt data
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        decrypted = (cipher.doFinal(encryptedBytes)).toString(Charsets.UTF_8)

    } catch (e: Throwable) {
        e.printStackTrace()
    }

    return decrypted
}