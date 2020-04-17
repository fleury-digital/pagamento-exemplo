package br.com.grupofleury.pagamento.presentation.view.cardControl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import br.com.grupofleury.pagamento.R
import br.com.grupofleury.pagamento.entities.Brand
import br.com.grupofleury.pagamento.entities.SavedCard
import kotlinx.android.synthetic.main.card_list_adapter.view.*

class CardControlAdapter : RecyclerView.Adapter<CardControlAdapter.ViewHolder>() {

    private var mSelectedItem: Int = -1
    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null
    var list = ArrayList<SavedCard>()

    fun add(items: List<SavedCard>) {
        list = items as ArrayList<SavedCard>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_list_adapter, parent, false)
        )
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], this)
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    private fun onItemHolderClick(itemHolder: ViewHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener?.onItemClick(null, itemHolder.itemView,
                itemHolder.adapterPosition, itemHolder.itemId)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(card: SavedCard, adapter: CardControlAdapter) = with(itemView) {
            this.tvCardNumber.text = context.getString(R.string.card_number, card.digits)

            when(card.getBrandType()) {
                Brand.AMEX -> this.ivCardFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa))
                Brand.MASTER_CARD -> this.ivCardFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mastercard))
                Brand.VISA -> this.ivCardFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa))
                else -> this.ivCardFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mastercard))
            }

            if(mSelectedItem == adapterPosition) {
                this.cardSelected.visibility = View.VISIBLE
                rbCard.isChecked = true
            } else {
                this.cardSelected.visibility = View.GONE
                rbCard.isChecked = false
            }

            val clickListener = View.OnClickListener {
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }

            this.rbCard.setOnClickListener(clickListener)
            itemView.setOnClickListener {
                adapter.onItemHolderClick(this@ViewHolder)
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }

        }

    }
}