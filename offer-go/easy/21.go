package easy

// 奇数在数组的前半部分，所有偶数在数组的后半部分
// 双指针 一个从前往后 一个从后往前
// 前指针，如果遇到一个偶数，就等后指针也遇到一个奇数，然后两个数位置交换
// [8,14,0,5,16,4,10,6,1]
// 20 ms 6.4 Mb
func exchange6Mb(nums []int) []int {
	for i, j := 0, len(nums)-1; i < j; {
		// 常规情况继续往下扫描
		// 常规情况前半部分应该是奇数
		preIsOdd := nums[i]%2 != 0
		// 常规情况后半部分应该是偶数
		lastIsEven := nums[j]%2 == 0
		if preIsOdd {
			i++
		}
		if lastIsEven {
			j--
		}
		// 如果前偶数后奇数 就改换位置了
		if !preIsOdd && !lastIsEven {
			nums[i], nums[j] = nums[j], nums[i]
			i++
			j--
		}
	}
	return nums
}

// 前指针一直走知道找到一个偶数
// 再走后指针，一直走直到找到一个奇数
// 交换位置
// 重复以上直到指针交叉循环退出
// 16 ms 6.4 Mb
func exchange(nums []int) []int {
	for i, j := 0, len(nums)-1; i < j; {
		for nums[i]&1 == 1 && i < j {
			i++
		}
		for nums[j]&1 == 0 && i < j {
			j--
		}
		nums[i], nums[j] = nums[j], nums[i]
	}
	return nums
}
