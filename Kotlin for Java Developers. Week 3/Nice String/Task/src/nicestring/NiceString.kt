package nicestring

fun String.isNice(): Boolean {
    return listOf(
        { listOf("bu", "ba", "be").none { this.contains(it) } },
        { this.count { it in "aeiou" } >= 3 },
        { this.zipWithNext().any { (first, second) -> first == second } }
    ).count { it.invoke() } >= 2
}