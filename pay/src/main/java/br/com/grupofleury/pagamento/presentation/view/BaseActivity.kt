package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

  fun openWhatsApp() {
    val number = "5511999179696"
    val url = "https://api.whatsapp.com/send?phone=$number"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
  }

}