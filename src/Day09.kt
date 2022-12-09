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

fun main() {
    fun getMoveVector(direction: String) = when (direction) {
        "R" -> 1 to 0
        "L" -> -1 to 0
        "U" -> 0 to 1
        "D" -> 0 to -1
        else -> error("invalid state")
    }

    fun part1(input: List<String>): Int {
        var head = 0 to 0
        var tail = 0 to 0
        val tailPositions = mutableSetOf(0 to 0)

        input.forEach {
            val (direction, moves) = it.split(" ")

            val moveVector = getMoveVector(direction)

            repeat(moves.toInt()) {
                head += moveVector

                if (head.distanceTo(tail) > sqrt(2.0)) {
                    val tailMove = (head - tail).coerceIn(-1..1)
                    tail += tailMove
                    tailPositions.add(tail)
                }
            }
        }

        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        var head = 0 to 0
        var tail = 0 to 0
        val tailPositions = mutableSetOf(0 to 0)

        input.forEach {
            val (direction, moves) = it.split(" ")

            val moveVector = getMoveVector(direction)

            repeat(moves.toInt()) {
                head += moveVector

                if (head.distanceTo(tail) > sqrt(2.0)) {
                    val tailMove = (head - tail).coerceIn(-1..1)
                    tail += tailMove
                    tailPositions.add(tail)
                }
            }
        }

        return tailPositions.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
