package com.thepascal.greenflag.repository

import com.thepascal.greenflag.models.User
import io.reactivex.Single

interface UserRepositoryContract {
    fun doesUserExist(email: String): Boolean
    fun doesUserExistReactively(email: String): Single<Boolean>
    //fun findUser(email:String, password: String): MutableList<User> //will be removed
    fun findUserReactively(email: String, password: String): Single<MutableList<User>>
    fun saveUser(user: User)
    fun saveUserReactively(user: User): Single<User>
    fun updateUser(user: User): Boolean
    fun deleteUser(email: String)
}