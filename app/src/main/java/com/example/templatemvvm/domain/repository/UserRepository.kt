package com.example.templatemvvm.domain.repository

import com.example.templatemvvm.data.models.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun loadUser(idUser: String): Single<User>
    fun signIn(user: User): Single<User>
    fun signUp(user: User): Single<User>
    fun getUser(): Single<User>
    fun logout(): Completable
}