package com.thepascal.greenflag.models

import android.os.Parcel
import android.os.Parcelable

data class AccountCreationFirstPage(val email: String, val password: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountCreationFirstPage> {
        override fun createFromParcel(parcel: Parcel): AccountCreationFirstPage {
            return AccountCreationFirstPage(parcel)
        }

        override fun newArray(size: Int): Array<AccountCreationFirstPage?> {
            return arrayOfNulls(size)
        }
    }
}