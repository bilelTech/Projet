package com.example.templatemvvm.mocks.fake

import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter

class FakeUserRepository : UserRepository {
    override fun loadUser(idUser: String): Single<User> {
        return createSingle(User(1,"bilel@gmail.com","1234567890","username","photourl"))
    }

    override fun signIn(user: User): Single<User> {
        return createSingle(User(1,"bilel@gmail.com","1234567890","username","photourl"))
    }

    override fun signUp(user: User): Single<User> {
        return createSingle(User(1,"bilel@gmail.com","1234567890","username","photourl"))
    }

    override fun getUser(): Single<User> {
        return createSingle(User(1,"bilel@gmail.com","1234567890","username","photourl"))
    }

    override fun logout(): Completable {
       return Completable.complete()
    }

    private fun <T> createSingle(emittedItem: T): Single<T> {
        return Single.create { emitter: SingleEmitter<T> -> emitter.onSuccess(emittedItem) }
    }
}