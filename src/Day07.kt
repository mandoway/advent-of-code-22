sealed class Path(val name: String, val parent: Directory?) {
    class Directory(name: String, parent: Directory? = null, val subPaths: MutableSet<Path> = mutableSetOf()) :
        Path(name, parent) {
        fun forEachDepthFirst(action: (Directory) -> Unit) {
            action(this)
            subPaths.forEach {
                if (it is Directory) {
                    it.forEachDepthFirst(action)
                }
            }
        }

        override fun toString(): String {
            return "Directory(name=$name)"
        }
    }

    class File(name: String, val fileSize: Int, parent: Directory? = null) : Path(name, parent) {
        override fun toString(): String {
            return "File(name=$name)"
        }
    }

    val size: Int
        get() = when (this) {
            is Directory -> subPaths.sumOf { it.size }
            is File -> fileSize
        }
}

fun getDirectorySizes(input: List<String>): List<Int> {
    val root = Path.Directory("/")

    var currentPath = root

    input.drop(1)
        .forEach {
            when {
                it.startsWith("$ cd") -> {
                    val newDirName = it.substringAfterLast(" ")

                    currentPath = when (newDirName) {
                        ".." -> {
                            currentPath.parent!!
                        }

                        "/" -> {
                            root
                        }

                        else -> {
                            val newDir = Path.Directory(newDirName, currentPath)
                            currentPath.subPaths.add(newDir)
                            newDir
                        }
                    }
                }

                it.startsWith("$ ls") -> {
                    // noop
                }

                it.startsWith("dir") -> {
                    // noop
                }

                else -> {
                    val filename = it.substringAfterLast(" ")
                    val filesize = it.substringBefore(" ").toInt()
                    val file = Path.File(filename, filesize, currentPath)

                    currentPath.subPaths.add(file)
                }
            }
        }

    val sizes = mutableListOf<Int>()

    root.forEachDepthFirst {
        sizes.add(it.size)
    }

    return sizes
}

fun main() {
    fun part1(input: List<String>): Int {
        return getDirectorySizes(input).filter { it < 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val totalDiskSpace = 70_000_000
        val neededDiskSpace = 30_000_000

        val sizes = getDirectorySizes(input)
        val usedDiskSpace = sizes[0]

        val unusedSpace = totalDiskSpace - usedDiskSpace
        val minimumDirectorySize = neededDiskSpace - unusedSpace

        return sizes.filter { it >= minimumDirectorySize }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
