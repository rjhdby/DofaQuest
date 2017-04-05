package dofamine.quest.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dofamine.quest.R
import dofamine.quest.activity.QuestScreen
import dofamine.quest.content.Content
import dofamine.quest.model.Quest
import org.jetbrains.anko.UI
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.startActivity


class QuestListAdapter(var context: Context) : RecyclerView.Adapter<QuestListAdapter.QuestViewHolder>() {
    var questsList: List<Quest> = ArrayList()

    class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.title) as TextView
        val description = itemView.findViewById(R.id.description) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.quest_list_item, parent, false)

        return QuestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val quest: Quest = Content.quests[position]
        holder.title.text = quest.name
        holder.description.text = quest.description
        holder.itemView.backgroundResource = R.color.quest_item_back
        when {
            quest.loaded -> holder.itemView.setOnClickListener { context.UI { startActivity<QuestScreen>("id" to position) } }
            else -> {
                holder.itemView.setOnClickListener { context.UI {
                    Content.loadQuest(position);notifyDataSetChanged()
                } }
                holder.itemView.backgroundResource = R.color.quest_item_back_download
            }
        }
    }

    public override fun getItemCount(): Int {
        return questsList.size
    }
}