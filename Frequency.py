class Solution:
    def maxFrequencyElements(self, nums: List[int]) -> int:
        output=0
        freq=[]
        for i in nums:
            freq.append(nums.count(i))
            if nums.count(i)>1:
                nums.remove(i)
        for i in freq:
            if i==max(freq):
                output+=i
        return output
