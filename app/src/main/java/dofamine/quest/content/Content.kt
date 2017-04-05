package dofamine.quest.content

import com.stanfy.gsonxml.GsonXmlBuilder
import com.stanfy.gsonxml.XmlParserCreator
import dofamine.quest.model.Quest
import dofamine.quest.util.Utils
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSink
import okio.Okio
import org.json.JSONArray
import org.json.JSONException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object Content {
    val quests: ArrayList<Quest> = ArrayList()
    val url = "http://motodtp.info/dofa/"
    val client = OkHttpClient()
    val listRequest: Request? = Request.Builder().url(url + "quests.json").build()

    fun init() {
        val temp = getQuestsList()
        val gsonXml = GsonXmlBuilder()
                .setXmlParserCreator(XmlParserCreator { return@XmlParserCreator XmlPullParserFactory.newInstance().newPullParser() })
                .create()
        File(Utils.contentPath).list()
                .filter { it.endsWith(".xml") }
                .forEach {
                    val quest = gsonXml.fromXml(BufferedReader(FileReader(Utils.file(it))), Quest::class.java)
                    val name = it.replace(".xml", "")
                    quest.filename = name
                    quest.loaded = true
                    temp.put(name, quest)
                }
        quests.clear()
        quests.addAll(temp.values.toTypedArray())
    }

    fun getQuestsList(): HashMap<String, Quest> {
        val loaded = HashMap<String, Quest>()
        if (listRequest == null) return loaded
        val result: String = client.newCall(listRequest).execute().body().string()
        try {
            val json = JSONArray(result)
            (0..json.length() - 1)
                    .map { json.getJSONObject(it) }
                    .forEach { loaded.put(it.getString("file"), Quest(it.getString("description"), it.getString("name"), it.getString("file"))) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return loaded
    }

    fun loadQuest(id: Int) {
        val response = client.newCall(Request.Builder().url(this.url + quests[id].filename + ".zip").build()).execute()
//        val length = response.header("Content-Length").toLong()

        val downloadedFile = Utils.file(quests[id].filename + ".zip")
        val sink: BufferedSink = Okio.buffer(Okio.sink(downloadedFile))
        sink.writeAll(response.body().source())
        sink.close()
        Utils.unzip(downloadedFile.name)
        downloadedFile.delete()
        init()
    }
}
