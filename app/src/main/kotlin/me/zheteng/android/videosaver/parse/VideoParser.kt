package me.zheteng.android.videosaver.parse

import okhttp3.*
import java.io.IOException


/**
 * 解析器框架代码
 */
class VideoParser private constructor() : Callback {
    private val mClient: OkHttpClient
    var callback: ParseCallback? = null
    private var mUrlProvider: UrlProvider? = null
    private var mExtractor: Extractor? = null
    private val mRequestInterceptors: MutableList<RequestInterceptor>


    init {
        val builder = OkHttpClient.Builder()

        mClient = builder.build()
        mRequestInterceptors = ArrayList<RequestInterceptor>()
    }

    fun addRequestInterceptor(requestInterceptor: RequestInterceptor) {
        mRequestInterceptors.add(requestInterceptor)
    }

    fun removeRequestInterceptor(requestInterceptor: RequestInterceptor) {
        mRequestInterceptors.remove(requestInterceptor)
    }

    override fun onFailure(call: Call, e: IOException) {
        if (callback != null) {
            callback!!.onFailure(this, e)
        }
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        val string = response.body().string()
        val list = mExtractor!!.extract(string)

        if (callback != null) {
            if (list.isNotEmpty()) {
                callback!!.onParsed(this, list)
            } else {
                callback!!.onFailure(this, NoVideoFoundException())
            }
        }
    }

    /**
     * 开始请求，异步操作，请使用[ParseCallback] 来处理结果
     */
    fun startParse() {
        val request = generateRequest()
        val call = mClient.newCall(request)
        call.enqueue(this)
    }

    /**
     * @return 生成请求
     */
    private fun generateRequest(): Request {
        val url = mUrlProvider!!.getTargetUrl()

        var builder = Request.Builder()
        builder = builder.url(url)
                .get()
                .header("User-Agent", "facebookexternalhit/1.1")
                .cacheControl(CacheControl.FORCE_NETWORK)
        for (interceptor in mRequestInterceptors) {
            builder = interceptor.intercept(builder)
        }

        return builder.build()
    }

    companion object {

        /**
         * 创建一个解析
         * @param urlProvider url provider
         * *
         * @param extractor 抽取算法
         * *
         * @return VideoParser
         */
        fun create(urlProvider: UrlProvider, extractor: Extractor): VideoParser {
            val instance = VideoParser()
            instance.mUrlProvider = urlProvider
            instance.mExtractor = extractor

            return instance
        }
    }
}
