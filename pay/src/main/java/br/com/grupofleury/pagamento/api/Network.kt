package br.com.grupofleury.pagamento.api

import br.com.grupofleury.pagamento.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

class Network {

  fun providesGson(): Gson =
    GsonBuilder().excludeFieldsWithModifiers(
      Modifier.FINAL,
      Modifier.TRANSIENT,
      Modifier.STATIC
    ).create()

  fun providesOkHttp(): OkHttpClient =
    OkHttpClient.Builder()
      .connectTimeout(60000L, TimeUnit.MILLISECONDS)
      .readTimeout(60000L, TimeUnit.MILLISECONDS)
      .writeTimeout(60000L, TimeUnit.MILLISECONDS)
      .build()

  fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(BuildConfig.URL)
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(okHttpClient)
      .build()

  fun providesIApi(retrofit: Retrofit): IApi = retrofit.create(IApi::class.java)

}