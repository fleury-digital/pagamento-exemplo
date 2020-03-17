package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Item
import br.com.grupofleury.pagamento.presentation.view.custom.ScheduleFinishedAdapter
import kotlinx.android.synthetic.main.schedule_finished_activity.*
import org.jetbrains.anko.alert


class ScheduleFinishedActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.schedule_finished_activity)
    listeners()
    setUp()
  }

  private fun listeners() {
    cancel.setOnClickListener { cancelAlert() }
    addToCalendar.setOnClickListener {
      addToCalendar(1584335553000L, 1584346353000L, "Campana - Exames", "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras justo odio, dapibus ac facilisis in, egestas eget quam.", "Minha casa")
    }
  }

  private fun addToCalendar(beginTime: Long, endTime: Long, title: String, description: String, location: String) {
    val intent: Intent = Intent(Intent.ACTION_INSERT)
      .setData(Events.CONTENT_URI)
      .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
      .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
      .putExtra(Events.TITLE, title)
      .putExtra(Events.DESCRIPTION, description)
      .putExtra(Events.EVENT_LOCATION, location)
      .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
    startActivity(intent)
  }

  private fun cancelAlert() {
    alert {
      title = "Condições de cancelamento"
      message =
        "Haverá uma cobrança de taxa de atendimento quando o cancelamento ocorrer com menos de 36h para a realização do exame"
      positiveButton(buttonText = "Entendi", onClicked = {
        it.dismiss()
      })
    }.show()
  }

  fun setUp() {
    val list = ArrayList<Item>()
    list.add(Item("5 exames", "R$563,00"))
    list.add(Item("Taxa de atendimento", "R$35,00"))
    total.text = "R$597,00"
    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = ScheduleFinishedAdapter()
    adapter.add(list)
    recyclerView.adapter = adapter
    adapter.notifyDataSetChanged()
  }

}