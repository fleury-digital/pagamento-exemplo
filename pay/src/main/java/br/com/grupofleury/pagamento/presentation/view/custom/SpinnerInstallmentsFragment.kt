package br.com.grupofleury.pagamento.presentation.view.custom

import android.R
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SpinnerInstallmentsFragment : DialogFragment() {

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
    arguments?.getStringArrayList(LIST)?.let { list ->
      val adapter: ListAdapter = object : ArrayAdapter<String>(
        requireContext(),
        R.layout.select_dialog_item,
        R.id.text1,
        list
      ) {}
      builder.setTitle("Número de parcelas")
        .setCancelable(true)
        .setAdapter(adapter) { dialog, pos ->
          dialog.dismiss()
          callback.selected(list[pos])
        }
      return builder.create()
    }
    return builder.create()
  }

  companion object {

    const val LIST = "list"
    private lateinit var callback: OnClickCallback

    fun newInstance(items: ArrayList<String>, callback: OnClickCallback): SpinnerInstallmentsFragment? {
      this.callback = callback
      val bundle = Bundle()
      bundle.putStringArrayList(LIST, items)
      val spinnerFragment = SpinnerInstallmentsFragment()
      spinnerFragment.arguments = bundle
      return spinnerFragment
    }
  }

  interface OnClickCallback {
    fun selected(id: String)
  }
}