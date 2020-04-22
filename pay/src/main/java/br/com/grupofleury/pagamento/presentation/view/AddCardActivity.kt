package br.com.grupofleury.pagamento.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.common.DateHelper
import br.com.grupofleury.pagamento.common.KeyboardHelper
import br.com.grupofleury.pagamento.entities.Card
import br.com.grupofleury.pagamento.presentation.view.custom.SpinnerInstallmentsFragment
import br.com.grupofleury.pagamento.presentation.view.custom.SpinnerInstallmentsFragment.OnClickCallback
import br.com.grupofleury.pagamento.presentation.viewmodel.AddCardViewModel
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.add_card_activity.*
import kotlinx.android.synthetic.main.add_card_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.util.*
import kotlin.collections.ArrayList


class AddCardActivity : BaseActivity(), DatePickerDialog.OnDateSetListener {

  private lateinit var viewModel: AddCardViewModel
  private var saveMyCard = false
  private var installmentsCurrent = 0
  private var cpfIsValid = false

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
    expires.setOnClickListener {
      createDialogWithoutDateField()
    }
    saveThisCard.onCheckedChange { _, isChecked ->
      saveMyCard = isChecked
    }
    birthdate?.editText?.setOnClickListener {
      //birthDate()
    }
    birthdate?.setOnClickListener {
      //birthDate()
    }
    expires?.editText?.setOnClickListener {
      createDialogWithoutDateField()
    }
    installments.setOnClickListener { showDialog() }
    spinner.setOnClickListener { showDialog() }
    pay.setOnClickListener { pay() }
    whats.setOnClickListener { openWhatsApp() }
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

  private fun birthDate() {
    val now = Calendar.getInstance()
    val dpd =
      DatePickerDialog.newInstance(
        this,
        now[Calendar.YEAR],
        now[Calendar.MONTH],
        now[Calendar.DAY_OF_MONTH]
      )
    dpd.show(supportFragmentManager, "Datepickerdialog")
  }

  private fun showDialog() {
    val listId = ArrayList<String>()
    for (i in 1..installmentsCurrent) {
      listId.add(i.toString())
    }
    val onClickCallback = object : OnClickCallback {
      override fun selected(id: String) {
        spinner.text = Editable.Factory.getInstance().newEditable(id)
      }
    }
    val spinnerFragment = SpinnerInstallmentsFragment.newInstance(listId, onClickCallback)
    spinnerFragment?.show(supportFragmentManager, "spinner")
  }

  private fun pay() {
    viewModel.saveCard(this, "MTYyMDUxMzY=", saveMyCard)
  }

  private fun setUp() {
    viewModel.get(this)
    cardNumber?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_CARD))
    expires?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_EXPIRES))
    cvv?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_CVV))
    cpf?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_CPF))
    birthdate?.editText?.addTextChangedListener(MascaraNumericaTextWatcher(PATTERN_BIRTH_DATE))
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
    cpf?.editText?.addTextChangedListener {
      it?.let { valid ->
        viewModel.cpfIsValid(valid.toString())
      }
    }
    birthdate?.editText?.addTextChangedListener {
      enablePayIfAllFilled()
    }
    owner?.editText?.addTextChangedListener {
      enablePayIfAllFilled()
    }
  }

  private fun enablePayIfAllFilled() {
    val numberTyped = cardNumber.editText?.text.toString()
    val cvvTyped = cvv.editText?.text.toString()
    val nameTyped = name.editText?.text.toString()
    val dateTyped = expires.editText?.text.toString()
    val cpfTyped = cpf.editText?.text.toString()
    val ownerTyped = owner.editText?.text.toString()
    val birthDateTyped = birthdate.editText?.text.toString()
    val cardModel = Card(numberTyped, cvvTyped, dateTyped, nameTyped, ownerTyped, cpfTyped, birthDateTyped)
    if (!cardModel.modelIsValid() || !cpfIsValid) {
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
    viewModel.cpfInvalid.observe(this, Observer {
      it?.let { cpfInvalid ->
        if (cpfInvalid) {
          cpf?.error = "CPF Inválido"
          cpfIsValid = false
          return@Observer
        }
        cpfIsValid = true
        cpf?.error = ""
      }
    })
    viewModel.creditCardSaved.observe(this, Observer {
      it?.let { saved ->
        if (saved) {
          startActivity(Intent(this, ScheduleFinishedActivity::class.java))
        }
      }
    })
  }

  private fun setUpInstallments(number: Int) {
    installments.editText?.text = Editable.Factory.getInstance().newEditable("1")
    installmentsCurrent = number
    installmentsMax.text = "Em até ${installmentsCurrent}x"
  }

  private fun createDialogWithoutDateField() {
    val calendar: Calendar = Calendar.getInstance()
    val yearSelected = calendar.get(Calendar.YEAR)
    val monthSelected = calendar.get(Calendar.MONTH)

    val dialogFragment = MonthYearPickerDialogFragment
      .getInstance(monthSelected, yearSelected)
    dialogFragment.show(supportFragmentManager, null)
    dialogFragment.setOnDateSetListener { year, monthOfYear ->
      expires.editText?.text = Editable.Factory.getInstance().newEditable("${DateHelper.format(monthOfYear + 1)}/${year}")
    }
  }

  override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
    val date = "${DateHelper.format(dayOfMonth)}/${DateHelper.format(monthOfYear + 1)}/${year}"
    birthdate.editText?.text = Editable.Factory.getInstance().newEditable(date)
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    KeyboardHelper.hide(layout)
    return super.dispatchTouchEvent(ev)
  }

  companion object {
    const val PATTERN_CARD = "#### #### #### ####"
    const val PATTERN_CPF = "###.###.###-##"
    const val PATTERN_EXPIRES = "##/####"
    const val PATTERN_BIRTH_DATE = "##/##/####"
    const val PATTERN_CVV = "###"
    const val MASTER_CARD_REGEX =
      "^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$"
    const val VISA_CARD_REGEX = "^4[0-9]{6,}$"
    const val BUTTON_ENABLED = 1f
    const val BUTTON_DISABLED = 0.4f
    const val CPF_LENGTH = 14
    const val DIALOG_FRAGMENT = 2020
  }

}