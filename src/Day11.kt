import kotlin.properties.Delegates

class Monkey(specification: List<String>) {
    private lateinit var items: MutableList<Int>
    private lateinit var operation: (old: Int) -> Int
    private var divisibleBy by Delegates.notNull<Int>()
    private var idIfTestIsTrue by Delegates.notNull<Int>()
    private var idIfTestIsFalse by Delegates.notNull<Int>()

    var inspections = 0

    init {
        specification.forEach {
            when {
                it.startsWith("Monkey") -> {
                    /* noop */
                }

                "Starting items" in it -> {
                    items = mutableListOf(
                        *it.substringAfterLast(":")
                            .trim()
                            .split(", ")
                            .map { el -> el.toInt() }
                            .toTypedArray()
                    )
                }

                "Operation" in it -> {
                    val operation = it.substringAfterLast("= ")
                    val (_, operator, parameter) = operation.split(" ")

                    val operatorFunction: Int.(Int) -> Int = when (operator) {
                        "+" -> Int::plus
                        "*" -> Int::times
                        else -> error("unsupported operator $operator")
                    }
                    val isParameterANumber = parameter.toIntOrNull() != null

                    this.operation = { old ->
                        if (isParameterANumber) {
                            old.operatorFunction(parameter.toInt())
                        } else {
                            old.operatorFunction(old)
                        }
                    }
                }

                "Test" in it -> divisibleBy = it.substringAfterLast(" ").toInt()

                "If true" in it -> idIfTestIsTrue = it.substringAfterLast(" ").toInt()

                "If false" in it -> idIfTestIsFalse = it.substringAfterLast(" ").toInt()
            }
        }
    }

    fun inspectItems(otherMonkeys: List<Monkey>) {
        items.forEach {
            val worryLevel = operation(it) / 3

            val destinationMonkeyId = if (worryLevel % divisibleBy == 0) {
                idIfTestIsTrue
            } else {
                idIfTestIsFalse
            }

            otherMonkeys[destinationMonkeyId].items.add(worryLevel)
            inspections++
        }

        items.clear()
    }
}

fun evaluateMonkeyBusiness(input: List<String>): Int {
    val monkeys = input.chunked(7).map { Monkey(it) }

    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.inspectItems(monkeys)
        }
    }

    return monkeys
        .map { it.inspections }
        .sortedDescending()
        .take(2)
        .reduce(Int::times)
}

fun main() {
    fun part1(input: List<String>): Int {
        return evaluateMonkeyBusiness(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
