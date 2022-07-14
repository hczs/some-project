package main

import (
	"fmt"
)

type ListNode struct {
	Val  int
	Next *ListNode
}

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

// 构造链表
func getListNode() *ListNode {
	head := ListNode{Val: 1}
	node1 := ListNode{Val: 2}
	node2 := ListNode{Val: 3}
	node3 := ListNode{Val: 4}
	node4 := ListNode{Val: 5}
	head.Next = &node1
	node1.Next = &node2
	node2.Next = &node3
	node3.Next = &node4
	return &head
}

// 打印链表
func printListNode(head *ListNode) {
	var output []int
	for head != nil {
		output = append(output, head.Val)
		head = head.Next
	}
	fmt.Println(output)
}

func main() {
	head := getListNode()
	printListNode(head)
	res := reverseList(head)
	printListNode(res)
}
