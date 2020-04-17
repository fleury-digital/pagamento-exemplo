package br.com.grupofleury.pagamento.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SavedCard(
    @SerializedName("id") var id: String,
    @SerializedName("customerId") var customerId: String,
    @SerializedName("digits") var digits: String,
    @SerializedName("token") var token: String,
    @SerializedName("brand") var brand: String,
    @SerializedName("name") var name: String
) : Serializable {

    fun getBrandType() : Brand? {
        return when(brand) {
            Brand.AMEX.name -> Brand.AMEX
            Brand.MASTER_CARD.name -> Brand.MASTER_CARD
            Brand.VISA.name -> Brand.VISA
            else -> null
        }
    }
}