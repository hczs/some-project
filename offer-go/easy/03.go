package easy

// tmp[0] = 0
// tmp[0] = -1
// if tmp[value] == -1
// repeat 0
func findRepeatNumber(nums []int) int {
	// 因为数组的 value 总是在 [0, len(nums) - 1] 之间
	// 所以可以把遍历过的 value 在 tmp 中对应下标做标记（可以把 tmp 想象成一个 map，数组下标是 key，对应值是 value）
	// 如果发现一个值是已经做过标记的了，证明已经遍历过了，判断重复
	var tmp = make([]int, len(nums))
	for _, value := range nums {
		if tmp[value] == -1 {
			return value
		} else {
			tmp[value] = -1
		}
	}
	return 0
}
