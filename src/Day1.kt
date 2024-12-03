import java.lang.Integer.sum
import kotlin.math.abs

fun main() {
    fun mapRowToPair(input: String): Pair<Int, Int> {
        return input.split(Regex("\\s+")).let {
            Pair(it[0].toInt(), it[1].toInt())
        }
    }
    fun sortLog(input: List<String>): List<Pair<Int, Int>> {
        val pairs = input.map { mapRowToPair(it) }
        val (numbersLeft, numbersRight) = pairs.unzip()
        val sortedPairs = numbersLeft.sorted().zip(numbersRight.sorted())
        return sortedPairs
    }

    check(5 == calculateRow(Pair(10, 5)))
    check(5 == calculateRow(Pair(3, 8)))
    check(5 == calculateRow(Pair(0, 5)))
    check(Pair(3, 5) == mapRowToPair("3 5"))
    check(Pair(23423423, 5) == mapRowToPair("23423423 5"))

    println("Solution Part one: " + solvePartOne(sortLog(readInput("day1"))));

    solvePartTwo(sortLog(readInput("day1")));
}

fun calculateRow(numbers: Pair<Int, Int>): Int {
    return abs(numbers.first - numbers.second)
}

private fun solvePartOne(sortedPairs: List<Pair<Int, Int>>): Int {
  return sortedPairs.sumOf { calculateRow(it) }
}

fun solvePartTwo(sortedPairs: List<Pair<Int, Int>>): Int {
    val (left, right) = sortedPairs.unzip()
    val leftMap = left.groupingBy { it }.eachCount().toMutableMap()
    val rightMap = right.groupingBy { it }.eachCount().toMutableMap()
    val occurrences = mutableMapOf<Int, Int>();
    left.forEach(fun(num: Int) {
        occurrences[num] = occurrences.getOrDefault(num, 0) + right.count { it == num }
    })
    println("Ergebnis diggi : " + leftMap.map { it.value * it.key *  rightMap.getOrDefault(it.key, 0)}.sum())
    println(leftMap);
    println(rightMap);
    println("Jannes: ${occurrences.map { it.key * it.value }.sum()}");
    println(left.size.toString() + " " + left.toSet().size)
    return 0;
}


