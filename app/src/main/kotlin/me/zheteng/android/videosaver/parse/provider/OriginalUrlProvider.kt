package me.zheteng.android.videosaver.parse.provider

import me.zheteng.android.videosaver.parse.UrlProvider

/**
 * 只是存储原始地址
 */
class OriginalUrlProvider(private val mTargetUrl: String) : UrlProvider {

    override fun getTargetUrl(): String {
        return mTargetUrl
    }
}
