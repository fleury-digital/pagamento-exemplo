package br.com.grupofleury.pagamento.repository.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class RemoteConfigService(private val remoteConfig: FirebaseRemoteConfig) {

  fun fetchAll(): MutableLiveData<Boolean> {
    val data = MutableLiveData<Boolean>()
    remoteConfig.fetch(0).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        remoteConfig.activateFetched()
        data.postValue(true)
        return@addOnCompleteListener
      }
      data.postValue(false)
    }
    return data
  }

  fun getInstallments() = remoteConfig.getLong(INSTALLMENTS)

  fun getCancelConditions() = remoteConfig.getString(CANCEL_CONDITIONS)

  companion object {
    const val INSTALLMENTS = "installments"
    const val CANCEL_CONDITIONS = "cancel_conditions"
  }

}