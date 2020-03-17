package br.com.grupofleury.pagamento.presentation.view.custom

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Item

class ScheduleResumeHolder(view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.title)
    val price = view.findViewById<TextView>(R.id.price)

    fun bind(item: Item) {
        title.text = item.title
        price.text = item.price
    }

}