package ru.gorbunova.tictactoe.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ru.gorbunova.tictactoe.App
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.concurrent.thread

object Images {

    fun load(url: String, call: (Bitmap?) -> Unit) {
        val result: (Bitmap?) -> Unit = {
            App.handler.post { call }
        }

        thread {
            try {
                val stream = if (url.startsWith("http"))
                    URL(url).openConnection().let {
                        it.connect()
                        it.getInputStream()
                    }
                else File(url).inputStream()
                stream.use {
                    BitmapFactory.decodeStream(stream).also {
                        call(it)
                    }
                }
            } catch (th: Throwable) {
                result(null)
            }
        }
    }
}