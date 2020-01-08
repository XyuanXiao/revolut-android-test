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
) : RecyclerView.Adapter<RecyclerAdapter.CurrencyHolder>() {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CurrencyHolder {
		val inflatedView = parent.inflate(R.layout.currency_item_layout, false)
		return CurrencyHolder(inflatedView)
	}

	override fun getItemCount() = rates.size

	override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
		val item = rates[position]
		holder.bindItem(item)
	}


	class CurrencyHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

		private var view: View = v
		private var rateItem: RateItem? = null

		init {
			v.setOnClickListener(this)
		}

		fun bindItem(item: RateItem) {
			rateItem = item
			view.currency_abbreviation.text = item.abbreviation
			view.currency_description.text = item.description
			view.currency_value.setText(item.value.toString(), TextView.BufferType.EDITABLE)
		}

		override fun onClick(v: View?) {
			TODO("not implemented")

		}
	}
}

