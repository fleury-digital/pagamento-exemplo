package br.com.grupofleury.pagamento.api

import br.com.grupofleury.pagamento.entities.Auth
import br.com.grupofleury.pagamento.entities.Optin
import br.com.grupofleury.pagamento.entities.Token
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface IApi {

  @POST("/v2/auth/token")
  fun auth(auth: Auth): Flow<Response<Token>>

  @POST
  fun cieloAuth(@Url url: String) : Deferred<Response<String>>

  @POST("customers/{customerId}/optin")
  fun optIn(
    @Path("customerId") customerId: String,
    @Body type: Optin
  ): Deferred<Response<Void>>

}