package me.zheteng.android.videosaver.parse

import android.text.TextUtils

/**
 * 工具类
 */
object ParserUtils {
    /**
     * Copied from [TextUtils.isEmpty]
     * Returns true if the string is null or 0-length.

     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

}
