package easy

// 挨个遍历，改变指向
func reverseList1(head *ListNode) *ListNode {
	var pre *ListNode
	cur := head
	for cur != nil {
		// 先暂存一下当前节点的下一个
		tmp := cur.Next
		// 改变当前节点指向前一个节点
		cur.Next = pre
		// 前节点更改为当前节点，准备改变下一个节点
		pre = cur
		// 更新当前节点为下一个节点指向
		cur = tmp
	}
	return pre
}

// 递归
func reverseList(head *ListNode) *ListNode {
	return recurList(head, nil)
}

func recurList(cur, pre *ListNode) *ListNode {
	if cur == nil {
		return pre
	}
	res := recurList(cur.Next, cur)
	cur.Next = pre
	return res
}
