package com.xyuan.revolut.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xyuan.revolut.R
import com.xyuan.revolut.inflate
import com.xyuan.revolut.model.RateItem
import kotlinx.android.synthetic.main.currency_item_layout.view.*

class RecyclerAdapter(
	private var rates: ArrayList<RateItem>,
	private val itemClickListener: OnItemClickListener,
	private val valueChangedListener: OnItemValueChangedListener
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
		holder.bindItem(rates, position, itemClickListener, valueChangedListener)
	}

	fun updateRates(newRates: ArrayList<RateItem>) {
		rates = newRates
		notifyDataSetChanged()
	}


	class CurrencyHolder(v: View) : RecyclerView.ViewHolder(v) {

		private var view: View = v
		private var rateItem: RateItem? = null

		fun bindItem(
			rates: ArrayList<RateItem>,
			position: Int,
			itemClickListener: OnItemClickListener,
			valueChangedListener: OnItemValueChangedListener
		) {
			rates[position].let { item ->
				rateItem = item
				view.position.text = (position + 1).toString()
				view.currency_abbreviation.text = item.abbreviation
				view.currency_description.text = item.description
				view.currency_value.apply {
					val value = if (item.value >= 0) item.value else 0f
					setText(value.toString(), TextView.BufferType.EDITABLE)
					clearFocus()
					addTextChangedListener(object : TextWatcher{
						override fun afterTextChanged(s: Editable?) {
							valueChangedListener.onValueChanged(
								item.abbreviation,
								s.toString().toFloat(),
								this@apply)
						}
						override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
						override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
					})
				}
				itemView.setOnClickListener{
					itemClickListener.onItemClicked(position)
				}
			}
		}
	}
}

interface OnItemClickListener{
	fun onItemClicked(position: Int)
}

interface OnItemValueChangedListener{
	fun onValueChanged(
		currency: String,
		value: Float,
		editText: EditText
	)
}

