fun shouldCheckSignalStrength(cycle: Int) = (cycle + 20) % 40 == 0

fun String.completionTime() = when (this) {
    "noop" -> 1
    else -> 2
}

fun retrieveSignalStrengths(commands: List<String>): Int {
    var cycle = 0
    var registerValue = 1
    var totalSignalStrength = 0

    commands.forEach { cmd ->
        val cyclesToComplete = cmd.completionTime()

        val addParam = if (cmd.startsWith("addx")) {
            cmd.split(" ").last().toInt()
        } else {
            0
        }

        repeat(cyclesToComplete) {
            cycle++

            if (shouldCheckSignalStrength(cycle)) {
                totalSignalStrength += cycle * registerValue
            }
        }

        registerValue += addParam
    }

    return totalSignalStrength
}

fun main() {
    fun part1(input: List<String>): Int {
        return retrieveSignalStrengths(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
