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
    File(".").walk()
            .filter {
                it.isFile && it.extension == "png" && it.parent == File(".").parent
            }.flatMap { file ->
                ImageIO.read(file).run {
                    sequenceOf(FilePic(file, getSubimage(304, 110, 240, 120)), FilePic(file, getSubimage(304, 240, 240, 120)))
                }
            }.forEachIndexed { index, data ->
                data.image.flush()
                File("./recipe-image/${data.file.name}-$index.png").run {
                    if (!exists()) {
                        createNewFile()
                        ImageIO.write(data.image, "png", this)
                    }
                }
            }
}

data class FilePic(val file: File, val image: BufferedImage)
