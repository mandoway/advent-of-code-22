fun parseRowsAndColumns(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
    val rows = input.map { row ->
        row.map { it.digitToInt() }
    }

    val cols = MutableList(input.size) { mutableListOf<Int>() }

    rows.forEach { row ->
        row.forEachIndexed { index, height ->
            cols[index].add(height)
        }
    }

    return rows to cols
}

fun List<Int>.blockedBy(height: Int) = if (isEmpty()) {
    false
} else {
    any { it >= height }
}

fun List<Int>.visibleTreesAt(height: Int) = when {
    isEmpty() -> 0
    all { it < height } -> size
    else -> takeWhile { it < height }.size + 1
}

fun List<Int>.takeBefore(index: Int) = take(index).reversed()
fun List<Int>.takeAfter(index: Int) = takeLast(lastIndex - index)

fun main() {
    fun part1(input: List<String>): Int {
        val (rows, cols) = parseRowsAndColumns(input)

        return rows.withIndex()
            .sumOf { (rowIndex, row) ->
                row.withIndex()
                    .count { (columnIndex, height) ->
                        val blockedFromLeft = row.takeBefore(columnIndex).blockedBy(height)
                        val blockedFromRight = row.takeAfter(columnIndex).blockedBy(height)

                        val currentColumn = cols[columnIndex]
                        val blockedFromTop = currentColumn.takeBefore(rowIndex).blockedBy(height)
                        val blockedFromBottom =
                            currentColumn.takeAfter(rowIndex).blockedBy(height)

                        !blockedFromLeft || !blockedFromRight || !blockedFromTop || !blockedFromBottom
                    }
            }
    }

    fun part2(input: List<String>): Int {
        val (rows, cols) = parseRowsAndColumns(input)

        return rows.withIndex()
            .maxOf { (rowIndex, row) ->
                row.withIndex()
                    .maxOf { (columnIndex, height) ->
                        val leftVisibleTrees = row.takeBefore(columnIndex).visibleTreesAt(height)
                        val rightVisibleTrees = row.takeAfter(columnIndex).visibleTreesAt(height)

                        val currentColumn = cols[columnIndex]
                        val topVisibleTrees = currentColumn.takeBefore(rowIndex).visibleTreesAt(height)
                        val bottomVisibleTrees = currentColumn.takeAfter(rowIndex).visibleTreesAt(height)

                        leftVisibleTrees * rightVisibleTrees * topVisibleTrees * bottomVisibleTrees
                    }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
