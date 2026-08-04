import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun generateNoiseTexturePng(
    size: Int = 512,
    whiteAlpha: Float = 0.18f,
    blackAlpha: Float = 0.30f,
    whiteChance: Float = 0.35f,
    outputFile: File
) {
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)

    for (x in 0 until size) {
        for (y in 0 until size) {
            val isWhite = Random.nextFloat() < whiteChance
            val intensity = (Random.nextFloat() + Random.nextFloat()) / 2f
            val maxAlpha = if (isWhite) whiteAlpha else blackAlpha
            val alpha = (maxAlpha * intensity * 255).toInt().coerceIn(0, 255)

            val rgb = if (isWhite) 0xFFFFFF else 0x000000
            val argb = (alpha shl 24) or rgb

            image.setRGB(x, y, argb)
        }
    }

    ImageIO.write(image, "png", outputFile)
    println("Saved to ${outputFile.absolutePath}, size=${outputFile.length()} bytes")
}

generateNoiseTexturePng(
    size = 128,
    whiteAlpha = 0.18f,
    blackAlpha = 0.30f,
    whiteChance = 0.35f,
    outputFile = File("noise_tile.png")
)