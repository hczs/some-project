package easy

// 常规法 一位一位的判断
// 0 ms 1.8 mb
func hammingWeight2Mb(num uint32) int {
	count := 0
	for num != 0 {
		if (num & 1) == 1 {
			count++
		}
		num >>= 1
	}
	return count
}

// 通过 n = n & (n - 1) 来消除 n 最低位的 1
// 例1：n = 7 (0111) n - 1 = 6 (0110)
// 0111 & 0110 = 0110 达到消除 0111 最低位 1 的效果
func hammingWeight(num uint32) int {
	var count int
	for count = 0; num != 0; count++ {
		num = num & (num - 1)
	}
	return count
}
