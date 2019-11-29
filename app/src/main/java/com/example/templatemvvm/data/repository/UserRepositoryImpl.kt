package com.example.templatemvvm.data.repository

import com.example.templatemvvm.data.local.UserDao
import com.example.templatemvvm.data.models.User
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject
constructor(
    private val userDao: UserDao,
    private val remoteService: RemoteService
) : UserRepository {

    override fun loadUser(idUser: String): Single<User> {
        return remoteService.getUser(idUser = idUser)
    }

    override fun signIn(user: User): Single<User> {
        return remoteService.signIn(email = user.email, password = user.password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (!it.error) {
                    val user = User(1, it.name, it.email, "", it.zipcode, it.country, it.city, it.apiKey)
                    userDao.insertUser(user)
                    return@flatMap Single.just(user)
                } else {
                     Single.error<User>(Throwable(it.message))
                }
            }
    }


    override fun signUp(user: User): Single<User> {
        return remoteService.signUp(user.name, user.email, user.password, user.zipCode, user.country, user.city)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap if (!it.error) signIn(user).map {
                    it
                } else {
                    Single.error<User>(Throwable(it.message))
                }
            }

    }

    override fun getUser(): Single<User> {
        if (userDao.getUser() == null) return Single.error(Throwable("user is empty"))
        return Single.just(userDao.getUser())
    }

    override fun logout(): Completable {

        userDao.clear()
        return Completable.complete()
    }
}