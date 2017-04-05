package dofamine.quest.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object Settings {
    lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun screenForQuest(name: String): Int {
        return preferences.getInt(name, 0)
    }

    fun hintsForQuest(name: String): Int {
        return preferences.getInt(name + "hint", -1)
    }

    fun setScreenForQuest(name: String, screen: Int) {
        preferences.edit().putInt(name, screen).commit()
    }

    fun setHintForQuest(name: String, screen: Int) {
        preferences.edit().putInt(name + "hint", screen).commit()
    }
}