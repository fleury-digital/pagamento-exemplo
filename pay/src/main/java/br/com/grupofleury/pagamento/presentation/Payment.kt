package br.com.grupofleury.pagamento.presentation

import br.com.braspag.braspag3ds.Braspag3ds
import br.com.braspag.braspag3ds.Environment

class Payment {
  companion object {
    fun pay() {
      Braspag3ds(
        accessToken = "",
        environment = Environment.SANDBOX
      )
    }
  }
}