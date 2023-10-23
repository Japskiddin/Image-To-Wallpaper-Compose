package io.github.japskiddin.imagetowallpaper.data

enum class AspectRatio(val width: Int, val height: Int) {
    Ratio4To3(width = 4, height = 3),
    Ratio3To4(width = 3, height = 4),
    Ratio16To9(width = 16, height = 9),
    Ratio9To16(width = 9, height = 16),
    Ratio18To9(width = 18, height = 9),
    Ratio9To18(width = 9, height = 18),
    RatioCustom(width = 0, height = 0);

    override fun toString(): String {
        return "$width:$height"
    }
}