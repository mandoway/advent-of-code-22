fun main() {
    fun sumBuckets(input: List<String>): List<Int> {
        var sum = 0
        return input.fold(emptyList()) { acc, s ->
            if (s.isBlank()) {
                (acc + sum).also {
                    sum = 0
                }
            } else {
                sum += s.toInt()
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
