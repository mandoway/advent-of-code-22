fun parseStacks(stackLines: List<String>): List<ArrayDeque<Char>> {
    val stacks = mutableListOf<ArrayDeque<Char>>()

    stackLines.forEach { line ->
        line.chunked(4)
            .map { it.trim() }
            .forEachIndexed { index, crate ->
                if (stacks.lastIndex < index) {
                    stacks.add(ArrayDeque(stackLines.size))
                }

                if (crate.isNotBlank()) {
                    val (_, crateChar, _) = crate.toCharArray()
                    stacks[index].addFirst(crateChar)
                }
            }
    }

    return stacks
}

data class Move(val amount: Int, val fromStack: Int, val toStack: Int) {
    val fromStackIndex get() = fromStack - 1
    val toStackIndex get() = toStack - 1
}

fun String.toMove(): Move {
    val parts = split(" ")

    return Move(
        amount = parts[1].toInt(),
        fromStack = parts[3].toInt(),
        toStack = parts[5].toInt()
    )
}

fun List<ArrayDeque<Char>>.perform(move: Move) {
    repeat(move.amount) {
        get(move.toStackIndex).addLast(
            get(move.fromStackIndex).removeLast()
        )
    }
}

fun List<ArrayDeque<Char>>.performKeepingOrder(move: Move) {
    val topCrates = mutableListOf<Char>()

    repeat(move.amount) {
        topCrates.add(get(move.fromStackIndex).removeLast())
    }

    get(move.toStackIndex).addAll(topCrates.reversed())
}

fun main() {
    fun parseInput(input: List<String>): Pair<List<ArrayDeque<Char>>, List<Move>> {
        val stackLines = input.takeWhile { it.isNotBlank() }.dropLast(1)
        val stacks = parseStacks(stackLines)

        val moveLines = input.dropWhile { it.isNotBlank() }.drop(1)
        val moves = moveLines.map { it.toMove() }
        return Pair(stacks, moves)
    }

    fun part1(input: List<String>): String {
        val (stacks, moves) = parseInput(input)

        moves.forEach { stacks.perform(it) }

        val topCrates = stacks.joinToString("") {
            it.last().toString()
        }

        return topCrates
    }

    fun part2(input: List<String>): String {
        val (stacks, moves) = parseInput(input)

        moves.forEach { stacks.performKeepingOrder(it) }

        return stacks.joinToString("") { it.last().toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
