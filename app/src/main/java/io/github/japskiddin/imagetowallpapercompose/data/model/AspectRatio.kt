package io.github.japskiddin.imagetowallpapercompose.data.model

const val RATIO_4_TO_3 = "4:3"
const val RATIO_3_TO_4 = "3:4"
const val RATIO_16_TO_9 = "16:9"
const val RATIO_9_TO_16 = "9:16"
const val RATIO_18_TO_9 = "18:9"
const val RATIO_9_TO_18 = "9:18"
const val RATIO_CUSTOM = "0:0"

class AspectRatio(val width: Int, val height: Int) {
    override fun toString(): String {
        return "$width:$height"
    }
}