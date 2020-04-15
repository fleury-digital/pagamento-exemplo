package br.com.grupofleury.pagamento.repository

import androidx.lifecycle.MutableLiveData
import br.com.grupofleury.pagamento.api.IApi
import br.com.grupofleury.pagamento.entities.Optin
import java.net.HttpURLConnection

class Repository(val api: IApi) : IRepository {

  override fun auth() {

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