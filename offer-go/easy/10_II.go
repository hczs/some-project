package easy

// 找规律
// n 个台阶的情况下
// 有两种情况，青蛙开始跳一级台阶，青蛙开始一下跳两级台阶
// 1.青蛙起手式跳一个台阶，剩下有 f(n-1) 种跳法
// 2.青蛙起手式跳两个台阶，剩下有 f(n-2) 种跳法
// 两种情况汇总就是 n 级台阶的跳法：f(n) = f(n-1) + f(n-2)
// 初始值和 fib 不一样
// 题目规定了：f(0) = 1 f(1) = 1
func numWays(n int) int {
	if n < 2 {
		return 1
	}
	pre2 := 1
	pre1 := 1
	cur := 2
	for i := 2; i <= n; i++ {
		cur = (pre1 + pre2) % 1000000007
		pre2 = pre1
		pre1 = cur
	}
	return cur
}
