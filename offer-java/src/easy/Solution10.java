package easy;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/7/12 11:13:02
 */
public class Solution10 {
    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int pre2 = 0;
        int pre1 = 1;
        int cur = 1;
        for (int i = 2; i <= n; i++) {
            cur = ( pre1 + pre2 ) % 1000000007;
            pre2 = pre1;
            pre1 = cur;
        }
        return cur;
    }
}
