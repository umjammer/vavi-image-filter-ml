/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.image.dlfilter;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import vavi.util.Debug;


class Test1 {

    public static void main(String[] args) throws Exception {
        Test1 app = new Test1();
        app.test();
    }

    @Test
    void test() throws Exception {

        Path in = Paths.get("src/test/resources/namacha.jpg");
        String fn = in.getFileName().toString();
        String base = fn.substring(0, fn.lastIndexOf('.'));
        String ext = fn.substring(fn.lastIndexOf('.'));

        AiasEsrGanSuperResolutionOp filter = new AiasEsrGanSuperResolutionOp();

        BufferedImage image = ImageIO.read(Files.newInputStream(in));

long t = System.currentTimeMillis();
        BufferedImage filteredImage = filter.filter(image, null);
Debug.println((System.currentTimeMillis() - t) + " ms");

        Path out = Paths.get("tmp", base + "(ESRGANx4)" + ext);
        if (!Files.exists(out.getParent())) {
            Files.createDirectories(out.getParent());
        }
        ImageIO.write(filteredImage, "PNG", Files.newOutputStream(out));
    }
}

/* */
