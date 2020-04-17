package br.com.grupofleury.pagamento.presentation.view.cardControl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.grupofleury.pagamento.entities.SavedCard

class CardControlViewModel : ViewModel() {

    val savedCards = MutableLiveData<List<SavedCard>>()

    fun getCards() {
        val cards = mutableListOf<SavedCard>()

        cards.add(SavedCard("5e833cdb3c454d6d708841c1", "VTJGc2RHVmtYMTlJYVdQalNGMWxaTE4rbXgySXBCMXNNUWxycnBJeEN6OD0=", "1234", "jfhgjhgbjgbj", "VISA", "Vitor Mazzola"))
        cards.add(SavedCard("5e833cdb3c454d6d708841c2", "VTJGc2RHVmtYMTlJYVdQalNGMWxaTE4rbXgySXBCMXNNUWxycnBJeEN6OD0=", "4356", "jfhgjhgbjgbj", "MASTER_CARD", "Vitor Mazzola"))
        //cards.add(SavedCard("5e833cdb3c454d6d708841c3", "VTJGc2RHVmtYMTlJYVdQalNGMWxaTE4rbXgySXBCMXNNUWxycnBJeEN6OD0=", "9876", "jfhgjhgbjgbj", "MASTER_CARD", "Vitor Mazzola"))
        //cards.add(SavedCard("5e833cdb3c454d6d708841c4", "VTJGc2RHVmtYMTlJYVdQalNGMWxaTE4rbXgySXBCMXNNUWxycnBJeEN6OD0=", "9872", "jfhgjhgbjgbj", "VISA", "Vitor Mazzola"))

        savedCards.postValue(cards)
    }
}