package br.com.grupofleury.pagamento.presentation.view.cardControl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.SavedCard
import br.com.grupofleury.pagamento.presentation.view.AddCardActivity
import br.com.grupofleury.pagamento.presentation.view.BaseActivity
import kotlinx.android.synthetic.main.activity_card_control.*
import kotlinx.android.synthetic.main.activity_card_control.recyclerView
import kotlinx.android.synthetic.main.add_card_toolbar.view.*

class CardControlActivity : BaseActivity() {

    private lateinit var viewModel: CardControlViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_control)
        viewModel = ViewModelProviders.of(this)[CardControlViewModel::class.java]
        configureToolbar()
        listeners()
        observers()
        viewModel.getCards()
    }

    private fun configureToolbar() {
        includedToolbar.title.text = "Formas de pagamento"
    }

    private fun listeners() {
        btAddCard.setOnClickListener { startActivity(Intent(this, AddCardActivity::class.java)) }
        btContinue.setOnClickListener { startActivity(Intent(this, AddCardActivity::class.java)) }
        includedToolbar.whats.setOnClickListener { openWhatsApp() }
        includedToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun observers() {
        viewModel.savedCards.observe(this, Observer {
            configureCards(it)
        })
    }

    private fun configureCards(cards: List<SavedCard>) {
        val adapter = CardControlAdapter()
        adapter.add(cards)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener(AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
            enableButton()
        })
    }

    private fun enableButton() {
        btContinue.backgroundTintList = ContextCompat.getColorStateList(this, R.color.colorAccent)
        btContinue.isEnabled = true
    }

    companion object {
        fun start(context: Activity) {
            context.startActivity(Intent(context, CardControlActivity::class.java))
        }
    }
}