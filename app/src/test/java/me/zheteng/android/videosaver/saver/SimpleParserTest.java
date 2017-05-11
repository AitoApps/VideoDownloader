package me.zheteng.android.videosaver.saver;

import static org.junit.Assert.assertTrue;

import android.text.TextUtils;

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
    // @Test
    // public void addition_isCorrect() throws Exception {
    //     assertEquals(4, 2 + 2);
    // }

    @Test
    public void parse_Normal() throws Exception {

        final CountDownLatch signal = new CountDownLatch(1);
        SimpleParser parser = new SimpleParser("416832257");
        parser.setCallback(new ParseCallback() {
            @Override
            public void onParsed(SimpleParser parser, String videoUrl) {
                signal.countDown();
                assertTrue(videoUrl, true);
            }

            @Override
            public void onFailure(SimpleParser parser, Exception e) {
                signal.countDown();
                assertTrue(e.getMessage(), false);
            }
        });
        parser.startParse();
        signal.await();
    }
}