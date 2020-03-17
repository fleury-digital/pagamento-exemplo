package br.com.grupofleury.pagamento.presentation.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Item


class ScheduleFinishedAdapter : RecyclerView.Adapter<ScheduleResumeHolder>() {

    var list = ArrayList<Item>()

    fun add(items: List<Item>) {
        list = items as ArrayList<Item>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleResumeHolder {
        return ScheduleResumeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.schedule_finished_adapter, parent, false)
        )
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: ScheduleResumeHolder, position: Int) {
        holder.bind(list[position])
    }

}