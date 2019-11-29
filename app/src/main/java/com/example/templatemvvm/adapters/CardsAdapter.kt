package com.example.templatemvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.templatemvvm.R
import com.example.templatemvvm.ui.dashboard.card.models.CardVO
import kotlinx.android.synthetic.main.card_item.view.*
import org.jetbrains.anko.image

class CardsAdapter constructor(
    private val onCardSelected:
        (CardVO) -> Unit
) :
    RecyclerView.Adapter<CardsAdapter.CardCellViewHolder>() {
    private val cards: MutableList<CardVO> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent,
            false
        )
        return CardCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun addCards(cards: List<CardVO>) {
        this.cards.addAll(cards)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CardCellViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card, onCardSelected)
    }

    class CardCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cardVO: CardVO, listener: (CardVO) -> Unit) = with(itemView) {
            img.image = ContextCompat.getDrawable(itemView.context, R.drawable.ic_img_bg)
            name.text = cardVO.name
            description.text = cardVO.description
            tvquantity.text = "${cardVO.quantity}"
            setOnClickListener { listener(cardVO) }
        }
    }
}