package real.droid.devtools.ui.text;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLFormatter implements ITextFormatter {
    @Override
    public CharSequence format(String text) {
        SpannableString ss = new SpannableString(text);
        String regex = "(\\S+)(=\"\\S+\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int nameStart = matcher.start(1);
            int nameEnd = matcher.end(1);

            int valueStart = matcher.start(2);
            int valueEnd = matcher.end(2);

            ss.setSpan(new ForegroundColorSpan(Color.RED), nameStart, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.BLUE), valueStart, valueEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        regex = "(<\\S+)|(</\\S+>)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(text);
        while (matcher.find()) {
            int nameStart = matcher.start(1);
            int nameEnd = matcher.end(1);
            ss.setSpan(new ForegroundColorSpan(0xFFB55500), nameStart, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        int index = 0;
        while ((index = text.indexOf(">", index)) > 0) {
            if (index > 1 && text.charAt(index - 1) == '/') {
                ss.setSpan(new ForegroundColorSpan(0xFFB55500), index - 1, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                index += 2;
            } else {
                ss.setSpan(new ForegroundColorSpan(0xFFB55500), index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                index += 1;
            }

        }
        return ss;
    }
}
