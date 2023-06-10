package io.yeahx4.realtimemovement.util

fun getRandomColor(): String {
    val random = java.util.Random()
    val value = random.nextInt(0xffffff + 1)

    return String.format("#%06x", value)
}
