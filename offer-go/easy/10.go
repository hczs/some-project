package easy

// 0 ms 1.9 MB
func fib2Mb(n int) int {
	store := make([]int, 101)
	store[0] = 0
	store[1] = 1
	for i := 2; i <= n; i++ {
		store[i] = ( store[i-1] + store[i-2] ) % 1000000007
	}
	return store[n]
}

// 0 ms 1.9 MB
func fib(n int) int {
	if n == 0 || n == 1 {
		return n
	}
	// 前两步的值
	pre2 := 0
	// 前一步的值
	pre1 := 1
	// 当前值
	cur := 1
	for i := 2; i <= n; i++ {
		cur = ( pre1 + pre2 ) % 1000000007
		// 更新前两步的值
		pre2 = pre1
		// 更新前一步的值
		pre1 = cur
	}
	return cur
}

