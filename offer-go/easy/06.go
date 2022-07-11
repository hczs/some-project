package easy

type ListNode struct {
	Val  int
	Next *ListNode
}

// 0 ms 3 MB
func reversePrint3Mb(head *ListNode) []int {
	res := make([]int, 0)
	for head != nil {
		res = append(res, head.Val)
		head = head.Next
	}
	// 首尾交换位置 达到从后往前排的效果
	for i, j := 0, len(res)-1; i < j; i, j = i+1, j-1 {
		res[i], res[j] = res[j], res[i]
	}
	return res
}

// 另一种方法 回溯
// 0 ms 4.5 MB
func reversePrint(head *ListNode) []int {
	res := make([]int, 0)
	recur(head, &res)
	return res
}

// 注意：go 中只有值传递，想要达到传递引用的效果，就直接传指针
// 参考：https://juejin.cn/post/6888117219213967368
// 官方文档：https://go.dev/ref/spec#Calls
func recur(head *ListNode, res *[]int) {
	if head == nil {
		return
	}
	recur(head.Next, res)
	*res = append(*res, head.Val)
}
