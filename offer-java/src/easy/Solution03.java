package easy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/7/11 15:02:32
 */
public class Solution03 {

    public int findRepeatNumber1ms(int[] nums) {
        int[] tmp = new int[nums.length];
        for (int num : nums) {
            if (tmp[num] == -1) {
                return num;
            } else {
                tmp[num] = -1;
            }
        }
        return 0;
    }

    public int findRepeatNumber4ms(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return 0;
    }
}
