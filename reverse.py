def length_of_longest_substring(s):
    start = 0
    max_length = 0
    seen = {}

    for end in range(len(s)):
        if s[end] in seen and seen[s[end]] >= start:
            start = seen[s[end]] + 1
        seen[s[end]] = end
        max_length = max(max_length, end - start + 1)

    return max_length


def main():
    s = input("Enter a string: ")
    result = length_of_longest_substring(s)
    print("Length of the largest substring without repeating characters:", result)


if __name__ == "__main__":
    main()
