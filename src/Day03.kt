fun main() {
    fun String.partitionRucksack(): Pair<String, String> {
        return substring(0, length / 2) to substring(length / 2)
    }

    fun intersectRucksack(first: String, second: String): Set<Char> {
        return first.toSet() intersect second.toSet()
    }

    fun Char.toPriority() = if (isLowerCase()) {
        code - 'a'.code + 1
    } else {
        code - 'A'.code + 27
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.partitionRucksack() }
            .map { (first, second) -> intersectRucksack(first, second).first() }
            .sumOf { it.toPriority() }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toSet() }
            .chunked(3)
            .map { it.reduce(Set<Char>::intersect).first() }
            .sumOf { it.toPriority() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
