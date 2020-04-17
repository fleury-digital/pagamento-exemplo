package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Item
import br.com.grupofleury.pagamento.presentation.view.cardControl.CardControlActivity
import br.com.grupofleury.pagamento.presentation.view.custom.ScheduleResumeAdapter
import br.com.grupofleury.pagamento.repository.remote.RemoteConfigService
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.schedule_resume_activity.*
import kotlinx.android.synthetic.main.schedule_resume_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class ScheduleResumeActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.schedule_resume_activity)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = ""
    listeners()
    setUp()
    loadInstallments()
  }

  private fun listeners() {
    cancelConditions.setOnClickListener { cancelAlert() }
    //pay.setOnClickListener { startActivity(Intent(this, AddCardActivity::class.java)) }
    pay.setOnClickListener { CardControlActivity.start(this) }
    whats.setOnClickListener { openWhatsApp() }
  }

  private fun loadInstallments(){
    val instance = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
      .build()
    instance.setDefaultsAsync(R.xml.remote_config_defaults)
    instance.setConfigSettingsAsync(configSettings)
    val remoteConfig = RemoteConfigService(instance)
    remoteConfig.fetchAll().observe(this, Observer {
      val inst = remoteConfig.getInstallments()
      installments.text = "Parcelado em até ${inst}x no cartão de crédito"
    })
  }


  private fun cancelAlert() {
    val instance = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
      .build()
    instance.setDefaultsAsync(R.xml.remote_config_defaults)
    instance.setConfigSettingsAsync(configSettings)
    val remoteConfig = RemoteConfigService(instance)
    alert {
      title = "Condições de cancelamento"
      message = remoteConfig.getCancelConditions()
      positiveButton(buttonText = "Entendi", onClicked = {
        it.dismiss()
      })
    }.show()
  }

  fun setUp() {
    val list = ArrayList<Item>()
    list.add(Item("Consectetur Bibendum", "R$10,00"))
    list.add(Item("Nibh Justo Venenatis Euismod", "R$130,00"))
    list.add(Item("Ultricies Justo", "R$110,00"))
    list.add(Item("Tortor Fusce Commodo Inceptos Ipsum", "R$210,00"))
    list.add(Item("Parturient Risus Ornare", "R$103,00"))
    total.text = "R$563,00"
    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = ScheduleResumeAdapter()
    adapter.add(list)
    recyclerView.adapter = adapter
    adapter.notifyDataSetChanged()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      android.R.id.home -> toast("Voltandoo")
    }
    return super.onOptionsItemSelected(item)
  }

}