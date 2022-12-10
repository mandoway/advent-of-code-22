import kotlin.math.pow
import kotlin.math.sqrt

fun Pair<Int, Int>.distanceTo(other: Pair<Int, Int>) =
    sqrt(
        (first - other.first).toDouble().pow(2)
                + (second - other.second).toDouble().pow(2)
    )

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun Pair<Int, Int>.coerceIn(range: IntRange) = first.coerceIn(range) to second.coerceIn(range)

fun Pair<Int, Int>.shouldMoveTo(other: Pair<Int, Int>) = distanceTo(other) > sqrt(2.0)

fun getMoveVector(direction: String) = when (direction) {
    "R" -> 1 to 0
    "L" -> -1 to 0
    "U" -> 0 to 1
    "D" -> 0 to -1
    else -> error("invalid state")
}

fun Pair<Int, Int>.unitMoveTowards(head: Pair<Int, Int>) = (head - this).coerceIn(-1..1)

fun getTotalTailPositions(input: List<String>, ropeLength: Int = 2): Int {
    val segments = MutableList(ropeLength) { 0 to 0 }
    val tailPositions = mutableSetOf(0 to 0)

    input.forEach {
        val (direction, moves) = it.split(" ")

        val moveVector = getMoveVector(direction)

        repeat(moves.toInt()) {
            segments[0] += moveVector

            segments
                .indices
                .zipWithNext()
                .forEach { (headIndex, tailIndex) ->
                    val head = segments[headIndex]
                    val tail = segments[tailIndex]

                    if (tail.shouldMoveTo(head)) {
                        segments[tailIndex] += tail.unitMoveTowards(head)
                    }
                }

            tailPositions.add(segments.last())
        }
    }

    return tailPositions.size
}


fun main() {
    fun part1(input: List<String>): Int {
        return getTotalTailPositions(input)
    }

    fun part2(input: List<String>): Int {
        return getTotalTailPositions(input, ropeLength = 10)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_test2")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
