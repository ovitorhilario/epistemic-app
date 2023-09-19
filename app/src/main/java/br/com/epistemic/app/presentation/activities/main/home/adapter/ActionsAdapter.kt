package br.com.epistemic.app.presentation.activities.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.epistemic.app.databinding.ItemActionBinding
import br.com.epistemic.app.presentation.activities.main.home.model.Action
import br.com.epistemic.app.commom.ext.load

class ActionsAdapter(private val items: ArrayList<Action>) : Adapter<ActionsAdapter.ActionHolder>() {

    inner class ActionHolder(binding: ItemActionBinding): ViewHolder(binding.root) {
        private val actionRoot = binding.root
        private val layoutAction = binding.layoutAction
        private val tvActionType = binding.tvActionType
        private val tvActionDescription = binding.tvActionDescription
        private val ivActionPicture = binding.ivActionPicture

        fun bind(action: Action) {
            tvActionType.text = action.type
            tvActionDescription.text = action.description
            ivActionPicture.load(action.imageUrl)
            action.backgroundRes?.let { layoutAction.background = it }
            actionRoot.setOnClickListener { action.onClick.invoke() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        return ActionHolder(
            ItemActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}