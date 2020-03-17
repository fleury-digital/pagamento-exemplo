package br.com.grupofleury.pagamento.entities

import com.google.gson.annotations.SerializedName

data class Token(
  @SerializedName("access_token") var accessToken: String,
  @SerializedName("token_type") var tokenType: String,
  @SerializedName("expires_in") var expiresIn: String
)