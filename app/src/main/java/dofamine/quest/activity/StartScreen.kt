package dofamine.quest.activity

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dofamine.quest.R
import dofamine.quest.adapters.QuestListAdapter
import dofamine.quest.content.Content
import dofamine.quest.util.Settings
import dofamine.quest.util.Utils


class StartScreen : AppCompatActivity() {
    private val questAdapter: QuestListAdapter = QuestListAdapter(this)
    lateinit private var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        prepareRecyclerView()
        Settings.init(this)
        Utils.init(this)
    }

    override fun onResume() {
        super.onResume()
        Content.init()
        questAdapter.questsList = Content.quests
        questAdapter.notifyDataSetChanged()
    }

    private fun prepareRecyclerView() {
        mRecyclerView = findViewById(R.id.questsList) as RecyclerView
        val mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = questAdapter
    }
}
