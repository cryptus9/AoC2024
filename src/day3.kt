fun main() {
    val rawData = readInput("day3").joinToString();
    solvePartOne(rawData)
    solvePartTwo(rawData)
}

private fun solvePartOne(rawData: String) {
    check(listOf("mul(2,4)") == matchAllInstructions("xmul(2,4)%&"))
    check(
        listOf(
            "mul(2,4)", "mul(5,5)", "mul(11,845)", "mul(8,5)"
        ) == matchAllInstructions("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]tmul(11,845345)hen(mul(11,845)mul(8,5))")
    )
    check(matchAllInstructions("mul(4*").isEmpty())
    check(matchAllInstructions("mul(6,9!").isEmpty())
    check(matchAllInstructions("?(12,34)").isEmpty())
    check(matchAllInstructions("mul ( 2 , 4 )").isEmpty())
    val instructions = matchAllInstructions(rawData)
    println("There were ${instructions.size} instructions found")

    check(8 == executeOperation("mul(2,4)"))
    check(8 == executeOperation("mul(2,4)"))
    check(161 == getSumOfMultplys(matchAllInstructions("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")))
    check(3330 == executeOperation("mul(333,10)"))

    val result = instructions.sumOf { executeOperation(it) }
    println(result)
}

private fun solvePartTwo(rawData: String) {
    check("xmul(2,4)&mul[3,7]!^?mul(8,5))" == cleanDataForPartTwo("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
    check("xmul(2,4)&mul[3,7]!^?mul(8,5))" == cleanDataForPartTwo("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))don't()mul434834893fjdfjn"))

    val rawDataPartTwo = cleanDataForPartTwo(rawData)
    val instructionsPartTwo = matchAllInstructions(rawDataPartTwo)
    println("There were ${instructionsPartTwo.size} instructions found for second part")
    val resultTwo = instructionsPartTwo.sumOf { executeOperation(it) }
    println(resultTwo)
}

fun cleanDataForPartTwo(input: String): String {
    val removeDisabledStatementsRegex = Regex("don't\\(\\).*?(do\\(\\)|$)")
    return input.replace(removeDisabledStatementsRegex, "")
}

fun getSumOfMultplys(instructions: List<String>): Int {
    return instructions.sumOf { executeOperation(it) }

}

fun executeOperation(operation: String): Int {
    val cleanedOperation = operation.replace(Regex("mul|\\(|\\)"), "")
    val factors = cleanedOperation.split(",")
    return factors.first().toInt() * factors.last().toInt()
}


fun matchAllInstructions(input: String): List<String> {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    return regex.findAll(input).map { it.groupValues.first() }.toList();
}