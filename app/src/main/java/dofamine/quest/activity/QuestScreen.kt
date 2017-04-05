package dofamine.quest.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.View
import android.view.View.*
import android.widget.ImageView
import dofamine.quest.R
import dofamine.quest.content.Content
import dofamine.quest.model.Quest
import dofamine.quest.model.Screen
import dofamine.quest.util.Settings
import dofamine.quest.util.Utils
import kotlinx.android.synthetic.main.activity_quest_screen.*
import org.jetbrains.anko.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class QuestScreen : AppCompatActivity() {
    var currentScreen: Int = 0
    lateinit var quest: Quest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_screen)
        quest = Content.quests[intent.extras.getInt("id")]
        currentScreen = Settings.screenForQuest(quest.filename)
        quest.screens[currentScreen].currentHint = Settings.hintsForQuest(quest.filename)
        draw()
    }

    override fun onResume() {
        super.onResume()
    }

    fun draw() {
        content.fullScroll(FOCUS_UP)
        controls.removeAllViews()
        images.removeAllViews()
        hints.removeAllViews()
        question.visibility = VISIBLE
        text.text = "  " + quest.screens[currentScreen].text.replace(Regex("(\n +| +\n)"), "\n").replace("\n", " ").replace("  ", "\n\n  ")
        val screen = quest.screens[currentScreen]
        screen.images.forEach { images.addView(photoView(it.image)) }
        hints.addView(hintsView(screen))
        when (screen.type) {
            "first" -> {
                question.text = getString(R.string.enter_code)
                controls.addView(firstScreenView())
                hints.visibility = GONE
            }
            "last" -> {
                question.visibility = GONE
                controls.addView(lastScreenView())
                hints.visibility = GONE
            }
            else -> {
                question.text = screen.question
                controls.addView(screenView())
                hints.visibility = VISIBLE
            }
        }
    }

    fun hintsView(screen: Screen): View {
        return UI {
            verticalLayout {
                (0..screen.currentHint).map { screen.hints[it].hint }.forEach {
                    textView {
                        textColor = Color.parseColor("#ff000000")
                        text = it
                    }
                }
                if (screen.hints.size - 1 > screen.currentHint) {
                    button("Подсказка") {
                        lparams(width = matchParent)
                        onClick {
                            hints.removeAllViews()
                            screen.currentHint++
                            hints.addView(hintsView(screen))
                            Settings.setHintForQuest(quest.filename, screen.currentHint)
                        }
                    }
                }
            }
        }.view
    }

    fun photoView(file: String): View {
        return UI {
            imageView {
                setImageDrawable(Drawable.createFromPath(Utils.contentPath + file))
                scaleType = ImageView.ScaleType.FIT_CENTER
                onClick {
                    content.visibility = GONE
                    fullScreenImage.visibility = VISIBLE
                    fullScreenImage.setImageDrawable(Drawable.createFromPath(Utils.contentPath + file))
                    fullScreenImage.onClick {
                        content.visibility = VISIBLE
                        fullScreenImage.visibility = GONE
                    }
                }
            }
        }.view
    }

    fun firstScreenView(): View {
        return UI {
            linearLayout {
                lparams(width = matchParent)
                val answer = editText {
                    inputType = TYPE_CLASS_NUMBER
                    lparams(weight = 1f)
                }
                button(context.getString(R.string.check_answer)) { onClick { if (checkCode(answer.text.toString())) nextScreen() else toast(context.getString(R.string.miss)) } }
            }
        }.view
    }

    fun screenView(): View {
        return UI {
            linearLayout {
                lparams(width = matchParent)
                val answer = editText { lparams(weight = 1f) }
                button(context.getString(R.string.check_answer)) {
                    onClick {
                        if (checkAnswer(answer.text.toString())) {
                            nextScreen()
                        } else toast(context.getString(R.string.wrong_answer))
                    }
                }
            }
        }.view
    }

    fun lastScreenView(): View {
        return UI {
            linearLayout {
                lparams(width = matchParent)
                button(context.getString(R.string.finish)) {
                    lparams(width = matchParent)
                    onClick {
                        startActivity<StartScreen>()
                        Settings.setScreenForQuest(quest.filename, 0)
                    }
                }
            }
        }.view
    }

    fun checkCode(code: String): Boolean {
        return code == "111"
    }

    fun checkAnswer(answer: String): Boolean {
        quest.screens[currentScreen].answers
                .forEach { if (it.answer.toLowerCase().replace(" ", "") == answer.toLowerCase().replace(" ", "")) return true }
        return false
    }

    fun nextScreen() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        currentScreen++
        Settings.setHintForQuest(quest.filename, -1)
        Settings.setScreenForQuest(quest.filename, currentScreen)
        draw()
    }
}
