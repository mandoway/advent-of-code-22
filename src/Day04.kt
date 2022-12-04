fun main() {

    fun String.toRange() = split("-")
        .map { it.toInt() }
        .let { it[0] .. it[1] }

    fun String.toRangePair() = split(",")
        .let { it[0].toRange() to it[1].toRange() }

    infix operator fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last

    infix fun IntRange.overlaps(other: IntRange) = first <= other.last && last >= other.first

    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toRangePair() }
            .count { (first, second) -> first in second || second in first }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toRangePair() }
            .count { (first, second) -> first overlaps second}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
