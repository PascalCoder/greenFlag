package com.thepascal.greenflag.models

data class AccountCreationSecondPage(
    val name: String,
    val username: String,
    val dateOfBirth: String,
    val country: String,
    val gender: String,
    val address: String?,
    val photo: Int
)