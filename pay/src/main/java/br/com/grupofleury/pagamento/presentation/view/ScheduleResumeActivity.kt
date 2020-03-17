package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Item
import br.com.grupofleury.pagamento.presentation.view.custom.ScheduleResumeAdapter
import kotlinx.android.synthetic.main.schedule_resume_activity.*

class ScheduleResumeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.schedule_resume_activity)
    pay.setOnClickListener { startActivity(Intent(this, AddCardActivity::class.java)) }
    listeners()
    setUp()
  }

  private fun listeners() {

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

}