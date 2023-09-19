package br.com.epistemic.app.presentation.activities.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.epistemic.app.databinding.ItemHomeActionsBinding
import br.com.epistemic.app.databinding.ItemHomeTopAppBarBinding
import br.com.epistemic.app.databinding.ItemHomeWelcomeBinding
import br.com.epistemic.app.presentation.activities.main.home.model.HomeItem
import br.com.epistemic.app.commom.ext.load
import br.com.epistemic.app.commom.singleton.Assets
import com.squareup.picasso.Picasso

class HomeAdapter(private val items: ArrayList<HomeItem>) : Adapter<ViewHolder>() {

    inner class TopAppBarHolder(binding: ItemHomeTopAppBarBinding): ViewHolder(binding.root) {
        private val ivHomeIcon = binding.ivHomeIcon
        private val btnHomeSettings = binding.btnHomeSettings

        fun bind(data: HomeItem.TopAppBar) {
            ivHomeIcon.load(Assets.EpistemicIcon)
            btnHomeSettings.setOnClickListener { data.onClick.invoke() }
            Picasso.get()
                .load(Assets.EpistemicIcon)
                .into(ivHomeIcon)
        }
    }

    inner class WelcomeHolder(binding: ItemHomeWelcomeBinding): ViewHolder(binding.root) {
        private val tvHomeName = binding.tvHomeName
        private val tvHomeDay = binding.tvHomeDay
        private val ivHomeAvatar = binding.ivHomeAvatar

        fun bind(data: HomeItem.Welcome) {
            tvHomeName.text = data.name
            tvHomeDay.text = data.date
            ivHomeAvatar.load(Assets.AvatarMan)
        }
    }

    inner class ActionsHolder(binding: ItemHomeActionsBinding): ViewHolder(binding.root) {
        private val rvActions = binding.rvActions

        fun bind(data: HomeItem.Actions) {
            val adapter = ActionsAdapter(data.list)
            rvActions.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            HOME_ITEM.TOP_APP_BAR.id -> TopAppBarHolder(ItemHomeTopAppBarBinding.inflate(inflater, parent, false))
            HOME_ITEM.WELCOME.id -> WelcomeHolder(ItemHomeWelcomeBinding.inflate(inflater, parent, false))
            HOME_ITEM.ACTIONS.id -> ActionsHolder(ItemHomeActionsBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Unknown View Type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TopAppBarHolder -> holder.bind(items[position] as HomeItem.TopAppBar)
            is WelcomeHolder -> holder.bind(items[position] as HomeItem.Welcome)
            is ActionsHolder -> holder.bind(items[position] as HomeItem.Actions)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is HomeItem.TopAppBar -> HOME_ITEM.TOP_APP_BAR.id
            is HomeItem.Welcome -> HOME_ITEM.WELCOME.id
            is HomeItem.Actions -> HOME_ITEM.ACTIONS.id
        }
    }

    enum class HOME_ITEM(val id: Int) {
        TOP_APP_BAR(0),
        WELCOME(1),
        ACTIONS(2)
    }
}