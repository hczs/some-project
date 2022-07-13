package easy

func deleteNode(head *ListNode, val int) *ListNode {
	if head.Val == val {
		return head.Next
	}
	pre := head
	cur := head.Next
	for cur != nil {
		if cur.Val == val {
			pre.Next = cur.Next
		}
		pre = cur
		cur = cur.Next
	}
	return head
}
