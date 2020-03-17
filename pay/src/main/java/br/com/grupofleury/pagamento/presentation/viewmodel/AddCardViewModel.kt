package br.com.grupofleury.pagamento.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddCardViewModel : ViewModel() {

  val installments = MutableLiveData<Int>()

  fun verifyInstallments() {
    installments.postValue(3)
  }

}