package br.com.grupofleury.pagamento.entities

import com.google.gson.annotations.SerializedName

data class Auth(
  @SerializedName("EstablishmentCode") var establishmentCode: String,
  @SerializedName("MerchantName") var merchantName: String,
  @SerializedName("MCC") var mcc: String
)