import org.w3c.dom.ranges.Range

fun main() {

    val rawData = readInput("day2");

    check(listOf(2, 5, 6, 10) == parseReport("2 5 6 10"))
    check(isReportSave(listOf(7, 6, 4, 2, 1), false))
    check(!isReportSave(listOf(1, 2, 7, 8, 9), true))
    check(!isReportSave(listOf(9, 7, 6, 2, 1), false))
    check(!isReportSave(listOf(1, 3, 2, 4, 5), true))
    check(!isReportSave(listOf(8, 6, 4, 4, 1), false))
    check(isReportSave(listOf(1, 3, 6, 7, 9), true))


    check(isReportSaveWithDampener(listOf(7, 6, 4, 2, 1), false))
    check(!isReportSaveWithDampener(listOf(1, 2, 7, 8, 9), true))
    check(!isReportSaveWithDampener(listOf(9, 7, 6, 2, 1), false))
    check(isReportSaveWithDampener(listOf(1, 3, 2, 4, 5), true))
    check(isReportSaveWithDampener(listOf(8, 6, 4, 4, 1), false))
    check(isReportSaveWithDampener(listOf(1, 3, 6, 7, 9), true))

    check(1 == solvePart2(listOf(listOf(10, 1, 3, 5, 7, 9, 11))))
    check(1 == solvePart2(listOf(listOf(10, 1, 3, 5, 7, 9))))
    check(1 == solvePart2(listOf(listOf(1, 3, 5, 7, 9, 20))))
    check(0 == solvePart2(listOf(listOf(1, 3, 5, 7, 50, 9, 20))))
    check(1 == solvePart2(listOf(listOf(1, 3, 5, 7, 9, -20))))
    check(0 == solvePart2(listOf(listOf(1, 3, 187, 7, 9, -20))))

    val reports = rawData.map { parseReport(it) }
    println("Day 2")
    println("${solvePart1(reports)} reports are safe")
    println("${solvePart2(reports)} reports are safe when considering the dampener")

}

fun parseReport(input: String): List<Int> {
    return input.split(" ").map { it.toInt() }
}

fun solvePart1(reports: List<List<Int>>): Int {
    var result = 0;
    for (report in reports) {
        val asc = report.first() < report.last()
        if (isReportSave(report, asc)) {
            result++
        }
    }
    return result
}

fun isReportSave(report: List<Int>, asc: Boolean): Boolean {
    val range = if (asc) IntRange(-3, -1) else IntRange(1, 3)
    if (report.size == 2) {
        return range.contains(report[0] - report[1])
    }
    if (!range.contains(report[0] - report[1])) {
        return false;
    }
    return isReportSave(report.drop(1), asc);
}

fun solvePart2(reports: List<List<Int>>): Int {
    var result = 0;
    for (report in reports) {
        if (isReportSaveWithDampener(report, report.first() < report.last())) {
            result++
            continue
        }
        if (isReportSaveWithDampener(report.drop(1), report[1] < report.last(), true)) {
            result++
            continue
        }
        if (isReportSaveWithDampener(report.dropLast(1), report.first() < report[report.size - 2], true)) {
            result++
        }
    }
    return result
}

fun isReportSaveWithDampener(report: List<Int>, asc: Boolean, toleratedProblem: Boolean = false): Boolean {
    val range = if (asc) IntRange(-3, -1) else IntRange(1, 3)
    if (report.size == 1) {
        return true;
    }
    if (report.size == 2) {
        if (!toleratedProblem) {
            return true;
        }
        return range.contains(report[0] - report[1])
    }
    if (!range.contains(report[0] - report[1])) {
        if (!toleratedProblem) {
            val cleanedReport = report.filterIndexed { i, _ -> i != 1 }
            return isReportSaveWithDampener(cleanedReport, asc, true)
        }
        return false;
    }
    return isReportSaveWithDampener(report.drop(1), asc, toleratedProblem);
}
