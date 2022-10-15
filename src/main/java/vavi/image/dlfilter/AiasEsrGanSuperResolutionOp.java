/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.image.dlfilter;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import me.aias.util.ImageUtils;
import me.aias.util.SuperResolutionTranslator;


/**
 * AiasEsrGanSuperResolutionOp.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2022-10-15 nsano initial version <br>
 */
public class AiasEsrGanSuperResolutionOp implements BufferedImageOp {

    /** */
    private static String modelUrl = "https://aias-home.oss-cn-beijing.aliyuncs.com/models/esrgan-tf2_1.zip";

    /** */
    private final Predictor<Image, Image> enhancer;

    /** */
    public AiasEsrGanSuperResolutionOp() {
        try {
            Criteria<Image, Image> criteria = Criteria.builder()
                    .setTypes(Image.class, Image.class)
                    .optModelUrls(modelUrl)
                    .optOption("Tags", "serve")
                    .optEngine("TensorFlow") // Use TensorFlow engine
                    .optOption("SignatureDefKey", "serving_default")
                    .optTranslator(new SuperResolutionTranslator())
                    .optProgress(new ProgressBar())
                    .build();
            ZooModel<Image, Image> model = criteria.loadModel();
            enhancer = model.newPredictor();
        } catch (IOException | ModelException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        try {
            Image inputImage = ImageUtils.bufferedImage2DJLImage(src);

            Image enhancedImages = enhancer.predict(inputImage);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            enhancedImages.save(baos, "png");
            baos.flush();

            return ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));

        } catch (IOException | TranslateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if (dstCM == null) {
            dstCM = src.getColorModel();
        }
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Double();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
}
