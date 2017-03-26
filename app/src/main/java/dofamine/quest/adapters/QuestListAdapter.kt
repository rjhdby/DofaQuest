package dofamine.quest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dofamine.quest.R
import dofamine.quest.model.Quest

class QuestListAdapter(private var moviesList: List<Quest>) : RecyclerView.Adapter<QuestListAdapter.QuestViewHolder>() {

    class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.title) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.quest_list_item, parent, false)

        return QuestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val quest: Quest = moviesList[position]
        holder.title.text = quest.description
    }

    public override fun getItemCount(): Int {
        return moviesList.size
    }
}