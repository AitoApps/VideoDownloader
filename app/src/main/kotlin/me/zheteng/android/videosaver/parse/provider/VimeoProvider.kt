package me.zheteng.android.videosaver.parse.provider

import me.zheteng.android.videosaver.parse.UrlProvider


/**

 */
class VimeoProvider(val id: String) : UrlProvider {

    override fun getTargetUrl(): String {
        return VIMEO_CONFIG_URL + id + "/config"
    }

    companion object {
        private val VIMEO_CONFIG_URL = "https://player.vimeo.com/video/"
    }
}
