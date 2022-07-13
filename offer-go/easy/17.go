package easy

// 关键确定循环的终点
// n = 1 -> 9
// n = 2 -> 99 = 9 * 10 + 9
// n = 3 -> 999 = 9 * 100 + 9 * 10 + 9
// end = 10^n - 1
// 8 ms 8 MB
func printNumbers8Mb(n int) []int {
	end := 1
	var result []int
	for n != 0 {
		end *= 10
		n--
	}
	for i := 1; i < end; i++ {
		result = append(result, i)
	}
	return result
}
