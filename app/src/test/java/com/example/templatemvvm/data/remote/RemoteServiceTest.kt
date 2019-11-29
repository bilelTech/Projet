package com.example.templatemvvm.data.remote

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.templatemvvm.data.models.User
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.Charset

@RunWith(JUnit4::class)
class RemoteServiceTest {

    private lateinit var webService: RemoteService
    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        webService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .build()
            .create(RemoteService::class.java)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun signInTest() {
        val content =
            this.javaClass.classLoader!!.getResource("mock/sign-in-response.json").readText(Charset.forName("UTF-8"))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val user = User(12, "bilel@gmail.com", "password", "username", "photourl")
        val response = webService.signIn(user.email, user.password).blockingGet()
        Assert.assertEquals(response, user)
    }

    @Test
    fun signUpTest() {
        val content =
            this.javaClass.classLoader!!.getResource("mock/sign-up-response.json").readText(Charset.forName("UTF-8"))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val user = User(12, "email", "password", "username", "photourl")
        val response = webService.signUp(user).blockingGet()
        Assert.assertEquals(response, user)
    }

    @Test
    fun getUserTest() {
        val content =
            this.javaClass.classLoader!!.getResource("mock/get-user-response.json").readText(Charset.forName("UTF-8"))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val user = User(12, "bilel@gmail.com", "password", "username", "photourl")
        val response = webService.getUser("12").blockingGet()
        Assert.assertEquals(response, user)
    }

    @Test
    fun getProductsTest() {
        val content =
            this.javaClass.classLoader!!.getResource("mock/get-products.json").readText(Charset.forName("UTF-8"))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val list =
            listOf(
                ProductData(
                    429617,
                    "product1",
                    7.7,
                    "efhieof fiehfie eighui zefhioghe fhioezgfheozi efihezog zoeihigohze ezgheozihez ezgfheoig",
                    "https://appyourself.net/wp-content/uploads/2018/08/android-app-erstellen-mit-app-baukasten-appyourself.png"
                )
                ,
                ProductData(
                    429616,
                    "product2",
                    7.7,
                    "efhieof fiehfie eighui zefhioghe fhioezgfheozi efihezog zoeihigohze ezgheozihez ezgfheoig",
                    "https://appyourself.net/wp-content/uploads/2018/08/android-app-erstellen-mit-app-baukasten-appyourself.png"
                )

            )
        val productListResult = ProductListResult(results = list)
        val response = webService.getProducts().blockingFirst()
        Assert.assertEquals(response, productListResult)
    }

    @Test
    fun getProductDetailTest() {
        val content =
            this.javaClass.classLoader!!.getResource("mock/get-product-detail.json").readText(Charset.forName("UTF-8"))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val product = ProductData(
            429617,
            "product1",
            7.7,
            "efhieof fiehfie eighui zefhioghe fhioezgfheozi efihezog zoeihigohze ezgheozihez ezgfheoig",
            "https://appyourself.net/wp-content/uploads/2018/08/android-app-erstellen-mit-app-baukasten-appyourself.png"
        )
        val response = webService.getProductDetail(12).blockingFirst()
        Assert.assertEquals(response, product)
    }
}