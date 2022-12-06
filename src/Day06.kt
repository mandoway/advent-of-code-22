fun main() {
    fun String.findStartOfMessageWithLength(packetLength: Int) = asSequence()
        .windowed(packetLength)
        .indexOfFirst { it.toSet().size == it.size }
        .plus(packetLength)

    fun part1(input: List<String>): Int {
        val packets = input.first()

        return packets.findStartOfMessageWithLength(4)
    }

    fun part2(input: List<String>): Int {
        return input.first().findStartOfMessageWithLength(14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
