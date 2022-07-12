package easy;

import java.util.ArrayDeque;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/7/12 9:19:37
 */
public class Solution09 {
    public static void main(String[] args) {
        CQueue queue = new CQueue();
        queue.appendTail(3);
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
    }
}

/**
 * 核心思想：栈1管入队，因为栈是后进先出，所以出队的时候，需要把栈1的内容都出队
 * 栈1内容都出到了栈2中，也就是栈1的栈底，现在是栈2的栈顶
 * 反过来想，栈1的栈底不就是第一个入队的元素嘛，所以栈2直接出栈即可，达到出队的效果
 * 直到栈2为空了，使用者还想出队，就需要再把栈1的数据拿到栈2中达到调换顺序的效果
 * 如果此时栈1也为空了，那么代表队列是空的，返回-1即可
 */
class CQueue {

    private final ArrayDeque<Integer> stack1;

    private final ArrayDeque<Integer> stack2;

    public CQueue() {
        stack1 = new ArrayDeque<>();
        stack2 = new ArrayDeque<>();
    }

    public void appendTail(int value) {
        stack1.push(value);
    }

    public int deleteHead() {
        // 正常情况栈2出栈即可
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }
        // 栈2为空 栈1东西挪到栈2换换顺序再出栈
        if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            return stack2.pop();
        }
        // 栈1和栈2都为空代表队列也是空的，返回-1
        return -1;
    }
}
