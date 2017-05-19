package me.zheteng.android.videosaver.parse.provider

import me.zheteng.android.videosaver.parse.UrlProvider

/**
 * 美拍URL拼接
 */
class MeipaiUrlProvider(private val mId: String) : UrlProvider {
    private val BASE_URL = "http://www.meipai.com/media/"
    /**
     * 页面id：http://www.meipai.com/media/{id}
     * http://www.meipai.com/media/416832257
     */
    override fun getTargetUrl(): String {
        return BASE_URL + mId
    }
}
