package br.com.grupofleury.pagamento.repository

import androidx.lifecycle.MutableLiveData
import br.com.braspag.silentorder.Environment
import br.com.braspag.silentorder.SilentOrderPost
import br.com.grupofleury.pagamento.api.IApi
import br.com.grupofleury.pagamento.entities.Optin
import java.net.HttpURLConnection

class Repository(val api: IApi) : IRepository {

  override fun auth(): MutableLiveData<String> {
    val data = MutableLiveData<String>()


    return data
  }

  override suspend fun cieloAuth(): MutableLiveData<String> {
    val data = MutableLiveData<String>()
    val url = "https://transactionsandbox.pagador.com.br/post/api/public/v1/accesstoken?merchantid=21b1716c-6ecb-49bd-ab09-d47b19e9e913"
    return data
  }

  override suspend fun optin(customerId: String, optIn: Optin): MutableLiveData<Boolean> {
    val data = MutableLiveData<Boolean>()
    val request = api.optIn(customerId, optIn)
    val response = request.await()
    when (response.code()) {
      HttpURLConnection.HTTP_CREATED -> data.postValue(true)
      HttpURLConnection.HTTP_CONFLICT -> data.postValue(true)
      else -> data.postValue(true)
    }
    return data
  }

}