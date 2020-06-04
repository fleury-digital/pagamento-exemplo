package br.com.grupofleury.pagamento.repository

import androidx.lifecycle.MutableLiveData
import br.com.grupofleury.pagamento.entities.Optin

interface IRepository {

  fun auth() : MutableLiveData<String>

  suspend fun optin(customerId: String, optIn: Optin): MutableLiveData<Boolean>
}