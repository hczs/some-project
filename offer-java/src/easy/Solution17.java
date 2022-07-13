package easy;

import java.util.Arrays;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/7/13 14:03:19
 */
public class Solution17 {

    int current = 0;

    int[] result;

    char[] table = new char[]{'0','1','2','3','4','5','6','7','8','9'};

    public int[] printNumbers(int n) {
        // 初始化总数
        result = new int[(int)(Math.pow(10, n) - 1)];
        char[] num = new char[n];
        dfs(num, 0);
        return result;
    }

    private void dfs(char[] num, int digit) {
        if (num.length == digit) {
            // 根据题意拒绝加0
            int curNum = Integer.parseInt(String.valueOf(num));
            if (curNum != 0) {
                result[current++] = curNum;
            }
            return;
        }
        for (int i = 0; i < table.length; i++) {
            // 给当前位赋值
            num[digit] = table[i];
            // 进入下一层
            dfs(num, digit + 1);
        }
    }

    public static void main(String[] args) {
        Solution17 solution17 = new Solution17();
        System.out.println(Arrays.toString(solution17.printNumbers(3)));
    }
}
