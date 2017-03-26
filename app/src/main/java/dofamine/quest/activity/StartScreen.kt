package dofamine.quest.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.stanfy.gsonxml.GsonXmlBuilder
import com.stanfy.gsonxml.XmlParserCreator
import dofamine.quest.R
import dofamine.quest.adapters.QuestListAdapter
import dofamine.quest.model.Quest
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class StartScreen : AppCompatActivity() {
    val quests: ArrayList<Quest> = ArrayList()
    lateinit private var mRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        val xml = BufferedReader(InputStreamReader(assets.open("quests/quest1.xml"), "UTF-8")).readText()
        val gsonXml = GsonXmlBuilder().setXmlParserCreator(XmlParserCreator { return@XmlParserCreator XmlPullParserFactory.newInstance().newPullParser() }).create()
        val quest = gsonXml.fromXml(xml, Quest::class.java)
        quests.add(quest)
        mRecyclerView = findViewById(R.id.questsList) as RecyclerView
        val questAdapter = QuestListAdapter(quests)

        val mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = questAdapter
        questAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

    }
}
