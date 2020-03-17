package br.com.grupofleury.pagamento.api

import br.com.grupofleury.pagamento.entities.Auth
import br.com.grupofleury.pagamento.entities.Token
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface IApi {

  @POST("/v2/auth/token")
  fun auth(auth: Auth): Flow<Response<Token>>

}