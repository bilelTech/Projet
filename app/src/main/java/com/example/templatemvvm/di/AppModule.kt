package com.example.templatemvvm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.templatemvvm.data.local.*
import com.example.templatemvvm.data.remote.RemoteService
import com.example.templatemvvm.data.repository.*
import com.example.templatemvvm.domain.repository.*
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(" http://192.168.2.112:90/PayPalServer/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@NonNull retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "database.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideInMemoryProductsCache(): ProductsCache {
        return MemoryProductsCache()
    }

    @Singleton
    @Provides
    @Named(DI.cachedDataStore)
    fun provideCachedProductsDataStore(productsCache: ProductsCache): ProductDataStore {
        return CachedProductDataStore(productsCache)
    }

    @Singleton
    @Provides
    @Named(DI.remoteDataStore)
    fun provideRemoteProductsDataSource(remoteService: RemoteService,userDao: UserDao): ProductDataStore {
        return RemoteProductsDataSource(remoteService,userDao)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        remoteService: RemoteService,
        @Named(DI.inMemoryCache) productsCache: ProductsCache,
        userDao: UserDao
    ): ProductsRepository {
        return ProductsRepositoryImpl(remoteService, productsCache,userDao)
    }

    @Provides
    @Singleton
    @Inject
    fun provideScreenRepository(
        @NonNull application: Application,
        screenDao: ScreenDao
    ): ScreenRepository {
        return ScreensRepositoryImpl(application,screenDao)
    }

    @Provides
    @Singleton
    fun provideUserDao(@NonNull database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(@NonNull database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCardDao(@NonNull database: AppDatabase): CardDao {
        return database.cardDao()
    }

    @Provides
    @Singleton
    fun provideScreenDao(@NonNull database: AppDatabase): ScreenDao {
        return database.screenDao()
    }

    @Singleton
    @Provides
    @Named(DI.favoritesCache)
    fun provideProductCacheRoom(productDao: ProductDao): FavoritesCache {
        return FavoritesRepository(productDao)
    }

    @Singleton
    @Provides
    @Named(DI.cardsCache)
    fun provideCardCacheRoom(cardDao: CardDao): CardCache {
        return CardRepository(cardDao)
    }
}