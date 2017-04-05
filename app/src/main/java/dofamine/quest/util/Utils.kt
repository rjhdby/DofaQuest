package dofamine.quest.util

import android.content.Context
import java.io.*
import java.util.zip.ZipInputStream

object Utils {
    lateinit var contentPath: String

    fun init(context: Context) {
        contentPath = context.getDir("quests", Context.MODE_PRIVATE).path + '/'
    }

    fun file(name: String): File {
        return File(contentPath, name)
    }

    fun unzip(zipName: String): Boolean {
        try {
            val zis = ZipInputStream(BufferedInputStream(FileInputStream(contentPath + zipName)))
            val buffer = ByteArray(1024)
            var count: Int

            while (true) {
                val ze = zis.nextEntry ?: break
                val out = FileOutputStream(contentPath + ze.name)
                while (true) {
                    count = zis.read(buffer)
                    if (count == -1) break
                    out.write(buffer, 0, count)
                }
                out.close()
                zis.closeEntry()
            }
            zis.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }
}