package dofamine.quest.model

import com.google.gson.annotations.SerializedName

class Hint {
    var content: String = ""
    @SerializedName("@index") var index: Int = 0
    var used = false
}