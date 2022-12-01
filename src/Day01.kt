fun main() {
    fun sumBuckets(input: List<String>): List<Int> {
        var sum1 = 0
        return input.fold(emptyList<Int>()) { acc, s ->
            if (s.isBlank()) {
                (acc + sum1).also {
                    sum1 = 0
                }
            } else {
                sum1 += s.toInt()
                acc
            }
        }
    }

    fun part1(input: List<String>): Int {
        return sumBuckets(input).max()
    }

    fun part2(input: List<String>): Int {
        return sumBuckets(input).sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
