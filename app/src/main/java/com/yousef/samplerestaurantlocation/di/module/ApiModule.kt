package com.yousef.samplerestaurantlocation.di.module

import android.os.Build
import android.util.Log
import com.yousef.samplerestaurantlocation.utils.Const
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*


@Module
class ApiModule {
    @Provides
    @Singleton
    fun requestApiDefault(): Retrofit {
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
        val specs: MutableList<ConnectionSpec> = ArrayList()
        specs.add(spec)
        specs.add(ConnectionSpec.COMPATIBLE_TLS)
        specs.add(ConnectionSpec.CLEARTEXT)


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .writeTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .readTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .connectionSpecs(specs)
            .addInterceptor(interceptor)
            .build()
//        val client = newHttpClient
        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @get:Singleton
    @get:Provides
    val newHttpClient: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .addInterceptor(interceptor)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .writeTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .readTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
            return enableTls12OnPreLollipop(client).build()
        }

    companion object {
        private const val CONNECT_TIMEOUT = 3
        fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
            if (Build.VERSION.SDK_INT in 19..21) {
                try {
                    val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm()
                    )
                    trustManagerFactory.init(null as KeyStore?)
                    val trustManagers: Array<TrustManager> = trustManagerFactory.getTrustManagers()
                    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                        ("Unexpected default trust managers:"
                                + Arrays.toString(trustManagers))
                    }
                    val trustManager: X509TrustManager = trustManagers[0] as X509TrustManager

                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
                    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory



                    val sc = SSLContext.getInstance("TLSv1.2")
                    sc.init(null, null, null)
                    client.sslSocketFactory(sslSocketFactory, trustManager)
                    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2).build()
                    val specs: MutableList<ConnectionSpec> = ArrayList()
                    specs.add(cs)
                    specs.add(ConnectionSpec.COMPATIBLE_TLS)
                    specs.add(ConnectionSpec.CLEARTEXT)
                    client.connectionSpecs(specs)


                } catch (exc: Exception) {
                    Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
                }
            }
            return client
        }
    }
}
