package br.com.grupofleury.pagamento.presentation.viewmodel

import androidx.lifecycle.*
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.api.Network
import br.com.grupofleury.pagamento.common.CPFValidator
import br.com.grupofleury.pagamento.entities.Optin
import br.com.grupofleury.pagamento.entities.OptinType
import br.com.grupofleury.pagamento.presentation.view.AddCardActivity
import br.com.grupofleury.pagamento.repository.IRepository
import br.com.grupofleury.pagamento.repository.Repository
import br.com.grupofleury.pagamento.repository.remote.RemoteConfigService
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.launch

class AddCardViewModel : ViewModel() {

  val installments = MutableLiveData<Int>()
  val cpfInvalid = MutableLiveData<Boolean>()
  val creditCardSaved = MutableLiveData<Boolean>()

  private lateinit var remoteConfig: RemoteConfigService
  private lateinit var repository: IRepository

  init {
    val network = Network()
    val gson = network.providesGson()
    val okHttp = network.providesOkHttp()
    val retrofit = network.providesRetrofit(gson, okHttp)
    val api = network.providesIApi(retrofit)
    repository = Repository(api)
    val instance = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
      .build()
    instance.setDefaultsAsync(R.xml.remote_config_defaults)
    instance.setConfigSettingsAsync(configSettings)
    remoteConfig = RemoteConfigService(instance)
  }

  fun get(lifecycleOwner: LifecycleOwner) {
    viewModelScope.launch {
      remoteConfig.fetchAll().observe(lifecycleOwner, Observer {
        it?.let { fetched ->
          verifyInstallments()
        }
      })
    }
  }

  fun sendCardData(cardName: String, cardNumber: String, expirationDate: String, cvv: String) {
    //Do something
  }

  fun saveCard(lifecycleOwner: LifecycleOwner, customerId: String, saveMyCard: Boolean) {
    if (!saveMyCard){
      creditCardSaved.postValue(true)
      return
    }
    val optin = Optin(OptinType.CREDIT_CARD.name)
    viewModelScope.launch {
      repository.optin(customerId, optin).observe(lifecycleOwner, Observer {
        it?.let { saved ->
          creditCardSaved.postValue(saved)
        }
      })
    }
  }

  fun cpfIsValid(cpf: String) {
    if (cpf.toString().count() != AddCardActivity.CPF_LENGTH) {
      return
    }
    val cpfIsValid = CPFValidator.isCPF(cpf.toString())
    if (!cpfIsValid) {
      cpfInvalid.postValue(true)
      return
    }
    cpfInvalid.postValue(false)
  }

  private fun verifyInstallments() {
    val current = remoteConfig.getInstallments()
    installments.postValue(current.toInt())
  }

}