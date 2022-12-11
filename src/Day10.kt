fun shouldCheckSignalStrength(cycle: Int) = (cycle + 20) % 40 == 0

fun String.completionTime() = when (this) {
    "noop" -> 1
    else -> 2
}

fun runSimulation(commands: List<String>, onEachCycle: (cycle: Int, registerValue: Int) -> Unit) {
    var cycle = 0
    var registerValue = 1

    commands.forEach { cmd ->
        val cyclesToComplete = cmd.completionTime()

        val addParam = if (cmd.startsWith("addx")) {
            cmd.split(" ").last().toInt()
        } else {
            0
        }

        repeat(cyclesToComplete) {
            cycle++

            onEachCycle(cycle, registerValue)
        }

        registerValue += addParam
    }
}

fun retrieveSignalStrengths(commands: List<String>): Int {
    var totalSignalStrength = 0

    runSimulation(commands) { cycle, registerValue ->
        if (shouldCheckSignalStrength(cycle)) {
            totalSignalStrength += cycle * registerValue
        }
    }

    return totalSignalStrength
}

fun drawCRTScreen(commands: List<String>) {
    val pixels = mutableListOf<Char>()

    runSimulation(commands) { _, registerValue ->
        val nextPixel = if (pixels.size % 40 in registerValue - 1..registerValue + 1) {
            '#'
        } else {
            '.'
        }

        pixels.add(nextPixel)
    }

    pixels.chunked(40)
        .forEach { println(it.joinToString("")) }
}

fun main() {
    fun part1(input: List<String>): Int {
        return retrieveSignalStrengths(input)
    }

    fun part2(input: List<String>) {
        drawCRTScreen(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
