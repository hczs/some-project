package easy

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 函数作用就是将转入节点的左右子树交换返回
func mirrorTree1(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}
	root.Left, root.Right = root.Right, root.Left
	mirrorTree(root.Left)
	mirrorTree(root.Right)
	return root
}

// 比上面内存少一点，少了个做交换时的中间变量
func mirrorTree(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}
	left := mirrorTree(root.Left)
	right := mirrorTree(root.Right)
	root.Left = right
	root.Right = left
	return root
}
