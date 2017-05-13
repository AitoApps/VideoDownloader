package me.zheteng.android.videosaver.saver;

import static org.junit.Assert.assertTrue;

import android.text.TextUtils;
import me.zheteng.android.videosaver.saver.extractor.MetaVideoExtractor;
import me.zheteng.android.videosaver.saver.extractor.MiaopaiExtractor;
import me.zheteng.android.videosaver.saver.provider.MeipaiUrlProvider;
import me.zheteng.android.videosaver.saver.provider.OriginalUrlProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@PrepareForTest(TextUtils.class)
public class SimpleParserTest {

    /**
     *
     * @param valid 是否测试的是可用地址，如果不是说明正确结果应该是走解析错误
     */
    private void request(final UrlProvider provider, Extractor extractor, final boolean valid, final CountDownLatch signal) {
        VideoParser parser = VideoParser.create(provider, extractor);
        parser.setCallback(new ParseCallback() {
            @Override
            public void onParsed(VideoParser parser, String videoUrl) {
                signal.countDown();
                String result = valid ? "passed" : "failed";
                System.out.println("Test " + result + ": " + provider.getClass().getSimpleName() + ",url: "+ videoUrl);
                assertTrue(videoUrl, valid);
            }

            @Override
            public void onFailure(VideoParser parser, Throwable e) {
                signal.countDown();
                String result = !valid ? "passed" : "failed";
                System.out.println("Test " + result + "");
                assertTrue(e.getMessage(), !valid);
            }
        });
        parser.startParse();
    }

    @Test
    public void parse_MeipaiValidId() throws Exception {
        List<String> ids = new ArrayList<>();
        ids.add("416832257");
        ids.add("729950410");
        ids.add("735302206");
        ids.add("748788265");
        ids.add("749651065");
        ids.add("748322880");
        ids.add("748027740");
        ids.add("749357877");
        ids.add("747967688");
        final CountDownLatch signal = new CountDownLatch(ids.size());

        for (final String id : ids) {
            UrlProvider provider = new MeipaiUrlProvider(id);
            Extractor extractor = new MetaVideoExtractor();
            request(provider, extractor, true, signal);
        }

        signal.await();
    }

    @Test
    public void parse_MeipaiInvalidId() throws Exception{
        final CountDownLatch signal = new CountDownLatch(1);

        final String id = "sdfsdfsdf";
        UrlProvider provider = new MeipaiUrlProvider(id);
        Extractor extractor = new MetaVideoExtractor();
        request(provider, extractor, false, signal);
        signal.await();
    }

    @Test
    public void parse_KuaishouValid() throws Exception {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.kuaishou.com/photo/425780666/2157280381");
        urls.add("https://www.kuaishou.com/photo/425780666/2125574649");
        urls.add("https://www.kuaishou.com/photo/425780666/2117347223");
        final CountDownLatch signal = new CountDownLatch(urls.size());
        for (String url : urls) {
            UrlProvider provider = new OriginalUrlProvider(url);
            Extractor extractor = new MetaVideoExtractor();
            request(provider, extractor, true, signal);
        }
        signal.await();
    }


    @Test
    public void parse_KuaishouInValid() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        String url = "https://www.kuaishou.com/photo/";
        UrlProvider provider = new OriginalUrlProvider(url);
        Extractor extractor = new MetaVideoExtractor();
        request(provider, extractor, false, signal);
        signal.await();
    }

    @Test
    public void parse_MiaopaiValid() throws Exception {
        List<String> urls = new ArrayList<>();
        urls.add("http://www.miaopai.com/show/0Wrff8C97TTK3eQRPmRWbrkFLA3oCPJi.htm");
        urls.add("http://www.miaopai.com/show/u5lcuztXXyBKwVBLSzVGRoiiWFV1RGaT.htm");
        urls.add("http://www.miaopai.com/show/GTy8b5AzIsO~rLzBgiCVtAXdVF8T647M.htm");
        urls.add("http://www.miaopai.com/show/Aq5aTZMXkFJ6rmdCLPlUHApj8pV8d8KF.htm");
        urls.add("http://www.miaopai.com/show/~OP~aVTpBxY3mKLF7oWVOKe5odfX19Et.htm");
        final CountDownLatch signal = new CountDownLatch(urls.size());
        for (String url : urls) {
            UrlProvider provider = new OriginalUrlProvider(url);
            Extractor extractor = new MiaopaiExtractor();
            request(provider, extractor, true, signal);
        }
        signal.await();
    }


    @Test
    public void parse_MiaopaiInValid() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        String url = "http://www.miaopai.com/show/xxx.htm";
        UrlProvider provider = new OriginalUrlProvider(url);
        Extractor extractor = new MiaopaiExtractor();
        request(provider, extractor, false, signal);
        signal.await();
    }

    @Test
    public void parse_InstagramValid() throws Exception {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.instagram.com/p/BUADWTyFxKz/");
        urls.add("https://www.instagram.com/p/BT9aWr1lfYe/");
        urls.add("https://www.instagram.com/p/BTmEtfUAUYC/");
        urls.add("https://www.instagram.com/p/BUA1e_bFSJa/");
        urls.add("https://www.instagram.com/p/BUA0td5l0P8/");
        final CountDownLatch signal = new CountDownLatch(urls.size());
        for (String url : urls) {
            UrlProvider provider = new OriginalUrlProvider(url);
            Extractor extractor = new MetaVideoExtractor();
            request(provider, extractor, true, signal);
        }
        signal.await();
    }
}