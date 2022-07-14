package easy

// 顺序查找，先计算长度，再找倒数第k个
// 0 ms 2.1 Mb
func getKthFromEnd2Mb(head *ListNode, k int) *ListNode {
	length := 0
	tmp := head
	for tmp != nil {
		length++
		tmp = tmp.Next
	}
	// 倒数第 k 个，也是正数第 len - k + 1 个
	end := length - k + 1
	// 循环 end - 1 次，head 刚好指向第 end 个节点
	for i := 1; i < end; i++ {
		head = head.Next
	}
	return head
}

// 快慢指针，一个 fast 先走 k 步，然后 slow 再和 fast 一起走，fast 走到头，返回 slow，此时 slow 自然是倒数第 k 个
// 0 ms 2.1 Mb
func getKthFromEnd(head *ListNode, k int) *ListNode {
	fast := head
	slow := head
	for k != 0 {
		fast = fast.Next
		k--
	}
	for fast != nil {
		slow = slow.Next
		fast = fast.Next
	}
	return slow
}
