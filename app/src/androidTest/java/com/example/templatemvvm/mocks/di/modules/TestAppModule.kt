package com.example.templatemvvm.mocks.di.modules

import com.example.templatemvvm.domain.repository.ProductsRepository
import com.example.templatemvvm.domain.repository.UserRepository
import com.example.templatemvvm.mocks.fake.FakeProductsRepository
import com.example.templatemvvm.mocks.fake.FakeUserRepository
import dagger.Provides
import javax.inject.Singleton

class TestAppModule(
    private val userRepository: UserRepository = FakeUserRepository(),
    private val productsRepository: ProductsRepository = FakeProductsRepository()
) {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    @Provides
    @Singleton
    fun provideProductsRepository(): ProductsRepository {
        return productsRepository
    }
}