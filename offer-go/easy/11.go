package easy

// 因为不是完全有序的数组，所以不能用传统的二分法
// 1 2 3 4 5
// 2 3 4 5 1
// 3 4 5 1 2
// 4 5 1 2 3
// 5 1 2 3 4
// 拿中间值与右边值来比较
// 如果小于，那么最小值在左边部分，继续搜索 [left, mid] mid也可能是最小值
// 如果大于，那么最小值在右边部分，继续搜索 [mid+1, right]
// 如果等于，只排除 right，继续搜索[left, right - 1]
// 结果 4 ms 3 MB
func minArray3Mb(numbers []int) int {
	length := len(numbers)
	if length == 0 {
		return 0
	}
	left := 0
	right := length - 1
	for left < right {
		mid := (left + right) / 2
		if numbers[mid] < numbers[right] {
			right = mid
		} else if numbers[mid] > numbers[right] {
			left = mid + 1
		} else {
			right = right - 1
		}
	}
	return numbers[left]
}

// 分治
// 任务分成两半
func minArray(numbers []int) int {
	return minArrayR(numbers, 0, len(numbers)-1)
}

func minArrayR(numbers []int, left, right int) int {
	if left+1 >= right {
		return min(numbers[left], numbers[right])
	}
	if numbers[left] < numbers[right] {
		return numbers[left]
	}
	// 这种写法不会溢出
	// (left + right) / 2 这种写法在left和right很大的时候会溢出
	mid := left + (right-left)/2
	return min(minArrayR(numbers, left, mid-1), minArrayR(numbers, mid, right))
}

func min(a, b int) int {
	if a > b {
		return b
	} else {
		return a
	}
}
