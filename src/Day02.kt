enum class Outcome(val points: Int) {
    LOSE(0),
    DRAW(3),
    WIN(6)
}

fun String.decrypt() = when (this) {
    "X" -> "A"
    "Y" -> "B"
    "Z" -> "C"
    else -> throw IllegalArgumentException("char must be in X, Y, Z")
}

val shapes = listOf("A", "B", "C")

fun String.pointsByShape() = shapes.indexOf(this) + 1

fun outcome(opponent: String, me: String): Outcome {
    val opponentIndex = shapes.indexOf(opponent)
    val myIndex = shapes.indexOf(me.decrypt())

    return when {
        (opponentIndex + 1) % shapes.size == myIndex -> Outcome.WIN
        (myIndex + 1) % shapes.size == opponentIndex -> Outcome.LOSE
        else -> Outcome.DRAW
    }
}

fun String.points(): Int {
    val (opponent, me) = split(" ")

    return outcome(opponent, me).points + me.decrypt().pointsByShape()
}

fun String.outcomeSecond() = when (this) {
    "X" -> Outcome.LOSE
    "Y" -> Outcome.DRAW
    "Z" -> Outcome.WIN
    else -> throw IllegalArgumentException()
}

fun String.points2(): Int {
    val (opponent, me) = split(" ")

    val outcome = me.outcomeSecond()

    val indexOfOpponent = shapes.indexOf(opponent)
    val myShape = when (outcome) {
        Outcome.LOSE -> indexOfOpponent - 1
        Outcome.DRAW -> indexOfOpponent
        Outcome.WIN -> indexOfOpponent + 1
    }
        .mod(shapes.size)
        .let { shapes[it] }

    return outcome.points + myShape.pointsByShape()
}

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { it.points2() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
