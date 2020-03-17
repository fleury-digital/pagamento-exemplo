package br.com.grupofleury.pagamento_exemplo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.grupofleury.pagamento.presentation.view.ScheduleResumeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, ScheduleResumeActivity::class.java))
    }

}