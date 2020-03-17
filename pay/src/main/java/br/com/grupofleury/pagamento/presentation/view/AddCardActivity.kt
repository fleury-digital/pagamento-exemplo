package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.common.KeyboardHelper
import br.com.grupofleury.pagamento.entities.Card
import br.com.grupofleury.pagamento.presentation.viewmodel.AddCardViewModel
import kotlinx.android.synthetic.main.add_card_activity.*
import kotlinx.android.synthetic.main.add_card_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.toast

class AddCardActivity : AppCompatActivity() {

  private lateinit var viewModel: AddCardViewModel
  private var saveMyCard = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.add_card_activity)
    viewModel = ViewModelProviders.of(this)[AddCardViewModel::class.java]
    listeners()
    setUp()
    observers()
    watchers()
  }

  private fun listeners() {
    saveThisCard.onCheckedChange { _, isChecked ->
      saveMyCard = isChecked
      if (isChecked) {
        toast("Salvando..")
      }
    }
    pay.setOnClickListener {
      startActivity(Intent(this, ScheduleFinishedActivity::class.java))
    }
    whats.setOnClickListener { whats() }
    cvvHelp.setOnClickListener {
        alert {
            message =
              "Cras justo odio, dapibus ac facilisis in, egestas eget quam. Cras mattis consectetur purus sit amet fermentum. Donec ullamcorper nulla non metus auctor fringilla."
            positiveButton(buttonText = "Entendi", onClicked = {
                it.dismiss()
            })
        }.show()
    }
  }

  private fun setUp() {
    viewModel.verifyInstallments()
    cardNumber?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_CARD))
    expires?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_EXPIRES))
    cvv?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_CVV))
  }

  private fun watchers() {
    cardNumber?.editText?.addTextChangedListener {
      it?.let { card ->
        if (card.isNotEmpty()) {
          verifyCard(card.toString())
          if (card.count() == Card.CARD_LENGHT) {
            enablePayIfAllFilled()
          }
        }
      }
    }
    expires?.editText?.addTextChangedListener {
      it?.let { exp ->
        if (exp.count() == Card.DATE_LENGHT) {
          enablePayIfAllFilled()
        }
      }
    }
    cvv?.editText?.addTextChangedListener {
      it?.let { cv ->
        if (cv.count() == Card.CVV_LENGHT) {
          enablePayIfAllFilled()
        }
      }
    }
    name?.editText?.addTextChangedListener {
      it?.let { nam ->
        if (nam.isNotEmpty()) {
          enablePayIfAllFilled()
        }
      }
    }
  }

  private fun enablePayIfAllFilled() {
    val numberTyped = cardNumber.editText?.text.toString()
    val cvvTyped = cvv.editText?.text.toString()
    val nameTyped = name.editText?.text.toString()
    val dateTyped = expires.editText?.text.toString()
    val cardModel = Card(numberTyped, cvvTyped, dateTyped, nameTyped)
    if (!cardModel.modelIsValid()) {
      pay.isEnabled = false
      pay.backgroundResource = R.drawable.btn_disabled
      return
    }
    pay.isEnabled = true
    pay.backgroundResource = R.drawable.btn_primary
  }

  private fun verifyCard(card: String) {
    val isMaster = card.substring(0, 1).toInt() == 5
    val isVisa = card.substring(0, 1).toInt() == 4
    if (isMaster) {
      brand.imageResource = R.drawable.mastercard
    } else if (isVisa) {
      brand.imageResource = R.drawable.visa
    } else {
      // load default
    }
  }

  private fun observers() {
    viewModel.installments.observe(this, Observer {
      it?.let { number ->
        setUpInstallments(number)
      }
    })
  }

  private fun setUpInstallments(number: Int) {
    installments.editText?.text = Editable.Factory.getInstance().newEditable(number.toString())
  }

  private fun whats() {

  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    KeyboardHelper.hide(layout)
    return super.dispatchTouchEvent(ev)
  }

  companion object {
    const val PATTERN_CARD = "#### #### #### ####"
    const val PATTERN_EXPIRES = "##/##"
    const val PATTERN_CVV = "###"
    const val MASTER_CARD_REGEX =
      "^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$"
    const val VISA_CARD_REGEX = "^4[0-9]{6,}$"
    const val BUTTON_ENABLED = 1f
    const val BUTTON_DISABLED = 0.4f
  }

}