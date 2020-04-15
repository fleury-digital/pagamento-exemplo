package br.com.grupofleury.pagamento.common

object DateHelper {
  fun format(month: Int): String {
    if (month>=10){
      return month.toString()
    }
    return "0$month"
  }
}