package com.jg.QrBusinessCard.CartListView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jg.QrBusinessCard.Models.BusinessCard
import com.jg.QrBusinessCard.R

class BusinessCardListAdapter(context: Context, entries: MutableList<BusinessCard>, private val clickListener: (BusinessCard) -> Unit) :
    RecyclerView.Adapter<BusinessCardListAdapter.ListViewHolder>(){

    var context : Context? = null
    private var entries: MutableList<BusinessCard> = java.util.ArrayList()

    init {
        this.context = context
        this.entries = entries
    }

    fun updateBusinessCardsList(businessCards: MutableList<BusinessCard>) {
        this.entries = businessCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.business_card_single_row, parent, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val card: BusinessCard = entries[position]
        holder.name.text = card.name + " " + card.surname



    }

    override fun getItemCount(): Int {
        return entries.size
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView


        init {
            name = itemView.findViewById(R.id.titleAction)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val card = entries[position]
                    clickListener(card) // Wywołanie clickListener z danymi karty
                }
        }
    }
}}