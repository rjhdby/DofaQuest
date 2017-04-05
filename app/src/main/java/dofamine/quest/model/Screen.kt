package dofamine.quest.model

import com.google.gson.annotations.SerializedName

class Screen {
    var text: String = ""
    @SerializedName("@type") var type: String = "screen"
    var answers: List<Answer> = ArrayList()
    var images: List<Image> = ArrayList()
    var hints: List<Hint> = ArrayList()
    var question: String = ""
    var currentHint = -1

    class Answer {
        @SerializedName("$") var answer: String = ""
    }

    class Image {
        @SerializedName("$") var image: String = ""
    }

    class Hint {
        @SerializedName("$") var hint: String = ""
    }
}