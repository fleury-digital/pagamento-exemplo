package br.com.grupofleury.pagamento.di

class NetworkModule {
//  @Provides
//  fun providesGson(): Gson =
//    GsonBuilder().excludeFieldsWithModifiers(
//      Modifier.FINAL,
//      Modifier.TRANSIENT,
//      Modifier.STATIC
//    ).create()
//
//  @Provides
//  fun providesOkHttp(): OkHttpClient =
//    OkHttpClient.Builder()
//      .addNetworkInterceptor(StethoInterceptor())
//      .connectTimeout(60000L, TimeUnit.MILLISECONDS)
//      .readTimeout(60000L, TimeUnit.MILLISECONDS)
//      .writeTimeout(60000L, TimeUnit.MILLISECONDS)
//      .build()
//
//  //
//  @Provides
//  fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
//    Retrofit.Builder()
//      .baseUrl(BuildConfig.URL)
//      .addCallAdapterFactory(CoroutineCallAdapterFactory())
//      .addConverterFactory(GsonConverterFactory.create(gson))
//      .client(okHttpClient)
//      .build()
//
//  @Provides
//  fun providesIApi(retrofit: Retrofit): IApi = retrofit.create(IApi::class.java)
}