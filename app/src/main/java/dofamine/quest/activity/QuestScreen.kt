package dofamine.quest.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.stanfy.gsonxml.GsonXmlBuilder
import com.stanfy.gsonxml.XmlParserCreator
import dofamine.quest.R
import dofamine.quest.model.Quest
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class QuestScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
    }

    override fun onResume() {
        super.onResume()
        try {
            val xml = BufferedReader(InputStreamReader(assets.open("quests/quest1.xml"), "UTF-8")).readText()
            val gsonXml = GsonXmlBuilder().setXmlParserCreator(XmlParserCreator { return@XmlParserCreator XmlPullParserFactory.newInstance().newPullParser() }).create()
            val quest = gsonXml.fromXml(xml, Quest::class.java)
            Log.e("TEXT", quest.description)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
