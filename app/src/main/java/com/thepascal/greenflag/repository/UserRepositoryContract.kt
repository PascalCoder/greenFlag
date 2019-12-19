package com.thepascal.greenflag.repository

import com.thepascal.greenflag.models.User

interface UserRepositoryContract {
    fun doesUserExist(email: String): Boolean
    fun findUser(email:String, password: String): MutableList<User>
    fun saveUser(user: User)
    fun updateUser(user: User): Boolean
    fun deleteUser(email: String)
}