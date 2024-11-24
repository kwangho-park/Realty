package kr.com.pkh.batch.util;

public class StringUtil {

    public static String removeAllCharsFromString(String str, char charToRemove) {
        StringBuilder sb = new StringBuilder();

        // 문자열을 순회하면서 특정 문자를 제거하고, 나머지 문자를 추가
        for (char c : str.toCharArray()) {
            if (c != charToRemove) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String padWithZero(String str) {
        if (str.length() == 1) {
            return "0" + str; // 한 자리 수인 경우 앞에 0을 추가하여 두 자리 수로 만듦
        }
        return str;         // 두 자리 수 이상인 경우 그대로 반환
    }

    public static boolean isEmpty(String str) {
        return (str != null && str != "") ? false : true;
    }

}
