package br.com.grupofleury.pagamento.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {

  fun hide(view: View?) {
    if (view != null) {
      val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
  }
}
