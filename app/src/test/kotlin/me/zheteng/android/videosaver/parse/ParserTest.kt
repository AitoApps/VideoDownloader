package me.zheteng.android.videosaver.parse

import me.zheteng.android.videosaver.parse.extractor.MetaVideoExtractor
import me.zheteng.android.videosaver.parse.extractor.MiaopaiExtractor
import me.zheteng.android.videosaver.parse.extractor.VimeoExtractor
import me.zheteng.android.videosaver.parse.extractor.YoutubeExtractor
import me.zheteng.android.videosaver.parse.interceptor.VimeoInterceptor
import me.zheteng.android.videosaver.parse.provider.MeipaiUrlProvider
import me.zheteng.android.videosaver.parse.provider.OriginalUrlProvider
import me.zheteng.android.videosaver.parse.provider.VimeoProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class ParserTest {

    /**
     * @param valid 是否测试的是可用地址，如果不是说明正确结果应该是走解析错误
     */
    private fun request(provider: UrlProvider,
                        extractor: Extractor,
                        valid: Boolean,
                        signal: CountDownLatch) {

        val parser = VideoParser.create(provider, extractor)
        if (provider is VimeoProvider) {
            parser.addRequestInterceptor(VimeoInterceptor(provider.id))
        }
        parser.callback = object : ParseCallback {
            override fun onParsed(parser: VideoParser, videos: List<Video>) {
                signal.countDown()
                val result = if (valid) "passed" else "failed"
                println(
                        "Test " + result + ": " + provider.javaClass.simpleName + ",url: " + videos[0].url)
                assertTrue("Parse success", valid)
            }

            override fun onFailure(parser: VideoParser, e: Throwable) {
                signal.countDown()
                val result = if (!valid) "passed" else "failed"
                println("Test " + result + "")
                assertTrue(e.message, !valid)
            }
        }
        parser.startParse()
    }

    /// ---------- 有效地址的测试 --------------
    @Test
    @Throws(Exception::class)
    fun parse_MeipaiValidId() {
        val ids = ArrayList<String>()
        ids.add("416832257")
        ids.add("729950410")
        ids.add("735302206")
        ids.add("748788265")
        ids.add("749651065")
        ids.add("748322880")
        ids.add("748027740")
        ids.add("749357877")
        ids.add("747967688")
        val signal = CountDownLatch(ids.size)

        for (id in ids) {
            val provider = MeipaiUrlProvider(id)
            val extractor = MetaVideoExtractor()
            request(provider, extractor, true, signal)
        }

        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_KuaishouValid() {
        val urls = ArrayList<String>()
        urls.add("https://www.kuaishou.com/photo/425780666/2157280381")
        urls.add("https://www.kuaishou.com/photo/425780666/2125574649")
        urls.add("https://www.kuaishou.com/photo/425780666/2117347223")
        val signal = CountDownLatch(urls.size)
        for (url in urls) {
            val provider = OriginalUrlProvider(url)
            val extractor = MetaVideoExtractor()
            request(provider, extractor, true, signal)
        }
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_MiaopaiValid() {
        val urls = ArrayList<String>()
        urls.add("http://www.miaopai.com/show/0Wrff8C97TTK3eQRPmRWbrkFLA3oCPJi.htm")
        urls.add("http://www.miaopai.com/show/u5lcuztXXyBKwVBLSzVGRoiiWFV1RGaT.htm")
        urls.add("http://www.miaopai.com/show/GTy8b5AzIsO~rLzBgiCVtAXdVF8T647M.htm")
        urls.add("http://www.miaopai.com/show/Aq5aTZMXkFJ6rmdCLPlUHApj8pV8d8KF.htm")
        urls.add("http://www.miaopai.com/show/~OP~aVTpBxY3mKLF7oWVOKe5odfX19Et.htm")
        val signal = CountDownLatch(urls.size)
        for (url in urls) {
            val provider = OriginalUrlProvider(url)
            val extractor = MiaopaiExtractor()
            request(provider, extractor, true, signal)
        }
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_InstagramValid() {
        val urls = ArrayList<String>()
        urls.add("https://www.instagram.com/p/BUADWTyFxKz/")
        urls.add("https://www.instagram.com/p/BT9aWr1lfYe/")
        urls.add("https://www.instagram.com/p/BTmEtfUAUYC/")
        urls.add("https://www.instagram.com/p/BUA1e_bFSJa/")
        urls.add("https://www.instagram.com/p/BUA0td5l0P8/")
        val signal = CountDownLatch(urls.size)
        for (url in urls) {
            val provider = OriginalUrlProvider(url)
            val extractor = MetaVideoExtractor()
            request(provider, extractor, true, signal)
        }
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_YoutubeValid() {
        val urls = ArrayList<String>()
        urls.add("https://www.youtube.com/watch?v=5Bec0w3FU4s")
        val signal = CountDownLatch(urls.size)
        for (url in urls) {
            val provider = OriginalUrlProvider(url)
            val extractor = YoutubeExtractor()
            request(provider, extractor, true, signal)
        }
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_VimeoValid() {
        val ids = ArrayList<String>()
        ids.add("216850854")
        val signal = CountDownLatch(ids.size)
        for (vid in ids) {
            val provider = VimeoProvider(vid)
            val extractor = VimeoExtractor()
            request(provider, extractor, true, signal)
        }
        signal.await()
    }

    /// ----------- 无效地址的测试 ------------
    @Test
    @Throws(Exception::class)
    fun parse_KuaishouInValid() {
        val signal = CountDownLatch(1)
        val url = "https://www.kuaishou.com/photo/"
        val provider = OriginalUrlProvider(url)
        val extractor = MetaVideoExtractor()
        request(provider, extractor, false, signal)
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_MeipaiInvalidId() {
        val signal = CountDownLatch(1)

        val id = "sdfsdfsdf"
        val provider = MeipaiUrlProvider(id)
        val extractor = MetaVideoExtractor()
        request(provider, extractor, false, signal)
        signal.await()
    }

    @Test
    @Throws(Exception::class)
    fun parse_MiaopaiInValid() {
        val signal = CountDownLatch(1)
        val url = "http://www.miaopai.com/show/xxx.htm"
        val provider = OriginalUrlProvider(url)
        val extractor = MiaopaiExtractor()
        request(provider, extractor, false, signal)
        signal.await()
    }
}