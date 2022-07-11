package easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/7/11 15:43:07
 */
public class Solution06 {

    public int[] reversePrint1ms(ListNode head) {
        List<Integer> tmp = new ArrayList<>();
        while (head != null) {
            tmp.add(head.val);
            head = head.next;
        }
        int[] res = new int[tmp.size()];
        int index = tmp.size() - 1;
        for (Integer num : tmp) {
            res[index] = num;
            index--;
        }
        return res;
    }

    List<Integer> tmp = new ArrayList<>();
    public int[] reversePrint(ListNode head) {
        recur(head);
        int[] res = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            res[i] = tmp.get(i);
        }
        return res;
    }

    /**
     * 另一种方法 递归回溯
     * @param head ListNode head
     */
    void recur(ListNode head) {
        if (head == null) {
            return;
        }
        // 不为空继续往下走直到走到链表最尾端
        recur(head.next);
        // 走到最尾端没法再走了就开始依次回溯 往 tmp 里面添加值
        tmp.add(head.val);
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
