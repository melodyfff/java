package algorithm.sort;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，它只包含a~z小写字母。把其中的重复字符移除，使得每个字符只出现一次。必须要确保返回的字符串是按词典顺序最小的结果。
 * <p>
 * 例如:
 * <p>
 * input: "bcabc"
 * output: "abc"
 * 注意，不能返回bca, 因为abc比bca要小，所以应该返回abc
 * <p>
 * input: "cbacdcbc"
 * output: “acdb”
 * <p>
 * class Solution
 * {
 * public:
 * string removeDuplicateLetters(string s)
 * {
 * }
 * };
 *
 * @author Xin Chen
 * @date 2017/12/6 16:29
 */
public class RemoveDuplicateLetters {

    public static String removeDuplicateLetters(String input) {
        Map<Character, Integer> map = new HashMap<>(16);
        StringBuilder sb = new StringBuilder();
        //初始化map
        for (char j = 'a'; j <= 'z'; j++) {
            map.put(j, 0);
        }

        char[] chars = input.toCharArray();
        for (char i : chars) {
            map.put(i, 1);
        }

        for (char s : map.keySet()) {
            if (map.get(s) == 1) {
                sb.append(s);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String result = removeDuplicateLetters("cabacdcbc");
        System.out.println(result);
    }
}
