import org.w3c.dom.ranges.Range

fun main(){

    val rawData = readInput("day2");

    check(listOf(2,5,6,10) == parseReport("2 5 6 10"))
    check(isReportSave(listOf(7,6,4,2,1), false))
    check(!isReportSave(listOf(1,2,7,8,9), true))
    check(!isReportSave(listOf(9,7,6,2,1), false))
    check(!isReportSave(listOf(1,3,2,4,5), true))
    check(!isReportSave(listOf(8,6,4,4,1), false))
    check(isReportSave(listOf(1,3,6,7,9), true))


    val reports = rawData.map { parseReport(it) }
    println( solve(reports))

}

fun parseReport(input: String): List<Int> {
    return input.split(" ").map { it.toInt() }
}

fun isReportSave(report: List<Int>, asc: Boolean): Boolean {
    val range = if (asc) IntRange(-3,-1) else IntRange(1,3)
    if (report.size == 2) {
        return range.contains(report[0] - report[1])
    }
    if (!range.contains(report[0] - report[1])) {
        return false;
    }
    return isReportSave(report.drop(1), asc);
}

fun solve(reports: List<List<Int>>): Int {
    var result = 0;
    for (report in reports) {
        val asc = report.first() < report.last()
        if (isReportSave(report, asc)) {
            result++
        }
    }
    return result
}
