package com.xyuan.revolut.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xyuan.revolut.R
import com.xyuan.revolut.inflate
import com.xyuan.revolut.model.RateItem
import kotlinx.android.synthetic.main.currency_item_layout.view.*

class RecyclerAdapter(
	private val rates: ArrayList<RateItem>
) : RecyclerView.Adapter<RecyclerAdapter.CurrencyHolder>(), OnItemClickListener {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CurrencyHolder {
		val inflatedView = parent.inflate(R.layout.currency_item_layout, false)
		return CurrencyHolder(inflatedView)
	}

	override fun getItemCount() = rates.size

	override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
		holder.bindItem(rates, position, this)
	}

	override fun onItemClicked(position: Int) {
		notifyItemMoved(position, 0) //This is not animating because of the notifyDataSetChanged() below
		val item = rates.removeAt(position)
		rates.add(0, item)
		notifyDataSetChanged()
	}


	class CurrencyHolder(v: View) : RecyclerView.ViewHolder(v) {

		private var view: View = v
		private var rateItem: RateItem? = null

		fun bindItem(
			rates: ArrayList<RateItem>,
			position: Int,
			itemClickListener: OnItemClickListener
		) {
			rates[position].let { item ->
				rateItem = item
				view.position.text = (position + 1).toString()
				view.currency_abbreviation.text = item.abbreviation
				view.currency_description.text = item.description
				view.currency_value.setText(item.value.toString(), TextView.BufferType.EDITABLE)
			}
			itemView.setOnClickListener{itemClickListener.onItemClicked(position)}
		}
	}
}

interface OnItemClickListener{
	fun onItemClicked(position: Int)
}

