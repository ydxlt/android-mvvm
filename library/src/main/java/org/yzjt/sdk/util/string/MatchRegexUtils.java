package org.yzjt.sdk.util.string;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by liuping on 18-4-29.
 */

public class MatchRegexUtils {

    public static boolean matchRegexCommon(String source, String reg) {
        if (TextUtils.isEmpty(source)) {
            return false;
        }
        return source.matches(reg);
    }

    public static void initEditTextMaxLengthListener(final EditText editText,
                                                     final int maxLength) {
        initEditTextMaxLengthListener(editText, maxLength, false);
    }

    public static void initEditTextMaxLengthListener(final EditText editText,
                                                     final int maxLength, final boolean allowSpaceKey) {

        if(editText == null){
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int editEnd = editText.getSelectionEnd();
                if (start > editEnd) {
                    return;
                }
                editText.removeTextChangedListener(this);
                //获取刚输入的内容
                String input = s.toString().substring(start, editEnd);
                if (!allowSpaceKey && input.contains(" ")) {
                    // 删除空格
                    String original = input.replaceAll("\\s*", "");
                    if (TextUtils.isEmpty(original)) {
                        s.delete(editEnd - input.length(), editEnd);
                        editEnd = editEnd - input.length();
                        editText.setSelection(editEnd);
                    } else {
                        s.delete(start, editEnd);
                        editEnd = editEnd - input.length();
                        editText.setSelection(editEnd);

                        editEnd = editEnd + original.length();
                        s.insert(start, original);
                        editText.setSelection(editEnd);
                    }
                }

                if (count == 2) {// 表情符号的字符长度最小为2
                    if (containsEmoji(input.toString())) {
                        if (StringUtil.counterChars(s.toString()) > maxLength && editEnd > 0) {
                            s.delete(editEnd - 2, editEnd);
                            editEnd = editEnd - 2;
                            editText.setSelection(editEnd);
                        }
                    }
                }

                while (StringUtil.counterChars(s.toString()) > maxLength && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                editText.setSelection(editEnd);
                editText.addTextChangedListener(this);
            }
        });

    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}
