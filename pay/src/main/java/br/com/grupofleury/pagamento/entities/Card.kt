package br.com.grupofleury.pagamento.entities

data class Card(
  var number: String,
  var cvv: String,
  var date: String,
  var name: String,
  var owner: String,
  var cpf: String,
  var birthDate: String
) {
  fun numberIsValid(): Boolean = number.count() == CARD_LENGHT

  fun cvvIsValid(): Boolean = cvv.count() == CVV_LENGHT

  fun dateIsValid(): Boolean = date.count() == DATE_LENGHT

  fun birthDateIsValid(): Boolean = birthDate.count() == BIRTH_DATE_LENGHT

  fun modelIsValid(): Boolean {
    val numberValid = numberIsValid()
    val birthValid = birthDateIsValid()
    val cvvValid = cvvIsValid()
    val dateValid = dateIsValid()
    val nameValid = name.isNotEmpty()
    val ownerValid = owner.isNotEmpty()
    return numberValid
      && birthValid
      && cvvValid
      && dateValid
      && nameValid
      && ownerValid
  }

  companion object {
    const val CARD_LENGHT = 19
    const val CVV_LENGHT = 3
    const val DATE_LENGHT = 7
    const val BIRTH_DATE_LENGHT = 10
  }
}