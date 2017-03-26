package dofamine.quest.model

import com.google.gson.annotations.SerializedName

class Screen {
    var text: String = ""
    @SerializedName("@type") var type: String = "screen"
    var answer: List<String>? = null
    var image: List<String>? = null
    var hint: List<Hint>? = null
    var question: String? = null
}