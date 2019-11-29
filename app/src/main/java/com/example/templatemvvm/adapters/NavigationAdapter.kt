package com.example.templatemvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.templatemvvm.R
import com.example.templatemvvm.ui.dashboard.profile.models.NavigationModel
import kotlinx.android.synthetic.main.item_nevigation.view.*

class NavigationAdapter constructor(
    private val onNavigationSelected:
        (NavigationModel) -> Unit
) :
    RecyclerView.Adapter<NavigationAdapter.NavigationCellViewHolder>() {
    private val navigationModels: MutableList<NavigationModel> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_nevigation,
            parent,
            false
        )
        return NavigationCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return navigationModels.size
    }

    fun addNavigation(navigationModel: List<NavigationModel>) {
        this.navigationModels.addAll(navigationModel)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NavigationCellViewHolder, position: Int) {
        val product = navigationModels[position]
        holder.bind(product, onNavigationSelected)
    }

    class NavigationCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nevigationModel: NavigationModel, listener: (NavigationModel) -> Unit) = with(itemView) {
            iconimg.setImageResource(nevigationModel.icon);
            image.setImageResource(nevigationModel.chevron)
            titletv.text = nevigationModel.title
            setOnClickListener { listener(nevigationModel) }
        }
    }
}