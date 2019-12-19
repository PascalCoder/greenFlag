package com.thepascal.greenflag.models

import android.os.Parcel
import android.os.Parcelable


data class User(
    val email: String?,
    val password: String?,
    val name: String,
    val username: String,
    val dateOfBirth: String,
    val country: String,
    val gender: String,
    val address: String?,
    val photo: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(dateOfBirth)
        parcel.writeString(country)
        parcel.writeString(gender)
        parcel.writeString(address)
        parcel.writeInt(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}