package br.com.grupofleury.pagamento.entities

data class Card(
  var number: String,
  var cvv: String,
  var date: String,
  var name: String
) {
  private fun isNumberIsValid(): Boolean = number.count() == CARD_LENGHT

  private fun isCvvIsValid(): Boolean = cvv.count() == CVV_LENGHT

  private fun isDateIsValid(): Boolean = date.count() == DATE_LENGHT

  fun modelIsValid(): Boolean {
    return isNumberIsValid()
      && isCvvIsValid()
      && isDateIsValid()
      && name.isNotEmpty()
  }

  companion object {
    const val CARD_LENGHT = 19
    const val CVV_LENGHT = 3
    const val DATE_LENGHT = 5
  }
}