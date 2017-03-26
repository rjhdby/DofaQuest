package dofamine.quest.model

import com.google.gson.annotations.SerializedName

class Quest {
    lateinit var screens: List<Screen>
    lateinit var description: String
    @SerializedName("@name") lateinit var name: String

    var currentScreen = 0
}
