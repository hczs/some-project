package easy

import "strings"

// 0 ms 3.2 MB
func replaceSpace3mb(s string) string {
	var result string
	for _, value := range s {
		if string(value) == " " {
			result += "%20"
		} else {
			result += string(value)
		}
	}
	return result
}

// 0 ms 1.9 MB
func replaceSpace(s string) string {
	// 最后一个 n 是 -1 代表全部替换
	// 参见注释：If n < 0, there is no limit on the number of replacements.
	return strings.Replace(s, " ", "%20", -1)
}
