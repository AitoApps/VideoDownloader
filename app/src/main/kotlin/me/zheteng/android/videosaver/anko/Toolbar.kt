package me.zheteng.android.videosaver.anko

import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar

/**
 * TODO 记得添加注释
 */
class HTML(val a: Int, val b: Int) {
    fun body() {  }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML(1, 2)  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}
class Toolbar : AnkoComponent<AppCompatActivity>{
    override fun createView(ui: AnkoContext<AppCompatActivity>) = with(ui) {
        toolbar {
            lparams()
            html(init = {
                // 带接收者的 lambda 由此开始
                body()   // 调用该接收者对象的一个方法
            })
        }
    }
}



