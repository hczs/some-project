package easy

// CQueue
// 入队：栈1
// 出队：栈1数据出队到栈2，达到先进先出的顺序后，栈2出队
type CQueue struct {
	stack1 *Stack
	stack2 *Stack
}

func Constructor() CQueue {
	return CQueue{
		stack1: NewStack(),
		stack2: NewStack(),
	}
}

func (this *CQueue) AppendTail(value int) {
	this.stack1.Push(value)
}

func (this *CQueue) DeleteHead() int {
	if this.stack2.Len() != 0 {
		return this.stack2.Pop()
	}
	if this.stack1.Len() != 0 {
		for this.stack1.Len() != 0 {
			this.stack2.Push(this.stack1.Pop())
		}
		return this.stack1.Pop()
	}
	return -1
}

/**
 * Your CQueue object will be instantiated and called as such:
 * obj := Constructor();
 * obj.AppendTail(value);
 * param_2 := obj.DeleteHead();
 */

// Stack 简单实现栈数据结构，有出栈入栈和当前长度功能
type Stack struct {
	element []int
}

func NewStack() *Stack {
	return &Stack{
		// 初始长度为 0 容量为 16 的切片
		element: make([]int, 0, 16),
	}
}

func (s Stack) Len() int {
	return len(s.element)
}

func (s *Stack) Push(value int) {
	s.element = append(s.element, value)
}

func (s *Stack) Pop() int {
	size := len(s.element)
	if len(s.element) == 0 {
		return -1
	}
	// 返回最末尾的元素，因为栈是后进先出
	result := s.element[size-1]
	// 把出栈的数据切掉
	s.element = s.element[:size-1]
	return result
}
