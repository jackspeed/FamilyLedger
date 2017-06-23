package ycj.com.familyledger.utils

import java.util.regex.Pattern

/**
 * @author: ycj
 * @date: 2017-06-23 17:22
 * @version V1.0 <>
 */
class RegexUtils {
    companion object {
        fun create(): RegexUtils = RegexUtils()
    }

    val REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$"
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    val REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"

    /**
     * 判断是否匹配正则

     * @param regex 正则表达式
     * *
     * @param input 要匹配的字符串
     * *
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    fun isMatch(regex: String, input: CharSequence?): Boolean {
        return input != null && input.length > 0 && Pattern.matches(regex, input)
    }

    /**
     * 验证手机号（精确）

     * @param input 待验证文本
     * *
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    fun isMobileExact(input: CharSequence): Boolean {
        return isMatch(REGEX_MOBILE_EXACT, input)
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    fun isDate(input: CharSequence): Boolean {
        return isMatch(REGEX_DATE, input)
    }
}