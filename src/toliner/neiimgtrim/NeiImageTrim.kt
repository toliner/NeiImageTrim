package toliner.neiimgtrim

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
            }.forEach { file ->
                ImageIO.read(file).run {
                    listOf(getSubimage(304, 110, 240, 120), getSubimage(304, 240, 240, 120))
                }.forEachIndexed { index, pic ->
                    pic.flush()
                    File("./recipe-image/${file.name}-$index.png").run {
                        if (!exists()) {
                            createNewFile()
                            ImageIO.write(pic, "png", this)
                        }
                    }
                }
            }
}
