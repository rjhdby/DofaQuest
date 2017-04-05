package dofamine.quest.model

import com.google.gson.annotations.SerializedName

class Quest {
    lateinit var screens: ArrayList<Screen>
    lateinit var description: String
    @SerializedName("@name") lateinit var name: String
    lateinit var filename: String
    var loaded = false

    constructor(description: String, name: String, filename: String) {
        this.description = description
        this.name = name
        this.filename = filename
    }
}
