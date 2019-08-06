package org.yzjt.sdk.util.string;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class StringUtil {

    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static String makeMd5(String source) {
        return MD5.getStringMD5(source);
    }

    public static final String filterUCS4(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        if (str.codePointCount(0, str.length()) == str.length()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < str.length()) {
            int codePoint = str.codePointAt(index);
            index += Character.charCount(codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                continue;
            }

            sb.appendCodePoint(codePoint);
        }

        return sb.toString();
    }

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    public static int[] cutStringFromAnchor(String source, int anchor, int targetLen) {
        int[] result = new int[]{0, 0};
        if(TextUtils.isEmpty(source) || targetLen < 0) {
            return result;
        }
        int len = source.length();
        if(anchor < 0 || anchor >= len) {
            anchor = len - 1;
        }
        int sourceLen = 0;
        List<Integer> sourceCharArray = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            sourceLen = sourceLen + getCharLen(source.charAt(i));
            sourceCharArray.add(sourceLen);
        }
        if(sourceLen <= targetLen) {
            return result;
        }
        int diff = sourceLen - targetLen;
        int begin = 0;
        int end = 0;
        int diffCharLen = 0;
        if(sourceCharArray.get(anchor) >= diff) {
            //only delete before anchor can satisfied
            end = anchor + 1;
            for(int i = anchor; i >= 0; i--) {
                diffCharLen = diffCharLen + sourceCharArray.get(i) - (i == 0 ? 0 : sourceCharArray.get(i - 1));
                if(diffCharLen >= diff) {
                    begin = i;
                    break;
                }
            }
        } else {
            //delete after anchor
            begin = 0;
            for(int i = anchor; i < len; i++) {
                if(sourceCharArray.get(i) >= diff) {
                    end = i + 1;
                    break;
                }
            }
        }
        result[0] = begin;
        result[1] = end;
        return result;
    }

    private static int getCharLen(char target) {
        int tmp = (int)target;
        return tmp > 0 && tmp < 127 ? 1 : 2;
    }

    public static String trimPunctuation(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char punct[] = { '\u002c', '\u002e', '\u0021', '\u003f', '\u003b', '\u003a', '\uff0c', '\u3002', '\uff01', '\uff1f',
                    '\uff1b', '\uff1a', '\u3001', '\u0025','\uff0f','\u002f'};
            boolean need_filter = false;
            for (int j = 0; j < punct.length; ++j) {
                if (punct[j] == str.charAt(i)) {
                    need_filter = true;
                    break;
                }
            }

            if (!need_filter) {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }
}
