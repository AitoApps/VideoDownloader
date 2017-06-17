package me.zheteng.android.videosaver

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import me.zheteng.android.videosaver.anko.Toolbar
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListActivityUi().setContentView(this)
    }

    fun tryLogin(ui: AnkoContext<ListActivity>, text: Editable?, text1: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}

data class Customer(val name: String,
                    val email: String)

class ListActivityUi : AnkoComponent<ListActivity> {
    private val customStyle = { v: Any ->
        when (v) {
            is Button -> v.textSize = 24f;
            is EditText -> v.textSize = 24f
        }
    }

    fun Context.attribute(value: Int): TypedValue {
        val ret = TypedValue()
        theme.resolveAttribute(value, ret, true)
        return ret
    }

    fun Context.attrAsDimen(value: Int): Int {
        return TypedValue.complexToDimensionPixelSize(attribute(value).data, resources.displayMetrics)
    }

    override fun createView(ui: AnkoContext<ListActivity>) = with(ui) {
        verticalLayout(theme = R.style.AppTheme) {
            Toolbar()
            val toolbar = toolbar() {
                backgroundResource = R.color.colorPrimary
                setTitleTextColor(Color.WHITE)
                id = R.id.toolbar
                lparams {
                    width = matchParent
                    height = ctx.attrAsDimen(R.attr.actionBarSize)
                }
            }
            ui.owner.setSupportActionBar(toolbar)

            imageView(R.mipmap.ic_launcher_round).lparams {
                margin = dip(16)
                gravity = Gravity.CENTER
            }
        }

    }.applyRecursively(customStyle)
}
