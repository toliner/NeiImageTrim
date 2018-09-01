package toliner.neiimgtrim

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    File("./recipe-image").run {
        if (!exists()) {
            mkdir()
        }
    }
    val blankImage = ImageIO.read(ClassLoader.getSystemResource("blank.png"))
    File(".").walk()
            .filter { file ->
                file.isFile && file.extension == "png"
            }.map { file ->
                FilePic(file, ImageIO.read(file))
            }.filter { data ->
                data.image.width == 854 && data.image.height == 480
            }.flatMap { data ->
                val fileName = "./recipe-image/${data.file.name}"
                sequenceOf(
                        FilePic(File("$fileName-0.png"), data.image.getSubimage(304, 110, 240, 120)),
                        FilePic(File("$fileName-1.png"), data.image.getSubimage(304, 240, 240, 120))
                )
            }.filter { data ->
                !data.file.exists()
            }.filter { data ->
                data.image.flush()
                (0 until data.image.width).any { x ->
                    (0 until data.image.height).any { y ->
                        data.image.getRGB(x, y) != blankImage.getRGB(x, y)
                    }
                }
            }.forEach { data ->
                data.image.flush()
                data.file.createNewFile()
                ImageIO.write(data.image, "png", data.file)
            }
}

data class FilePic(val file: File, val image: BufferedImage)
