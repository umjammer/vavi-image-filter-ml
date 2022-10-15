[![Release](https://jitpack.io/v/umjammer/vavi-image-filter-ml.svg)](https://jitpack.io/#umjammer/vavi-image-filter-ml)
[![Java CI](https://github.com/umjammer/vavi-image-filter-ml/actions/workflows/maven.yml/badge.svg)](https://github.com/umjammer/vavi-image-filter-ml/actions/workflows/maven.yml)
[![CodeQL](https://github.com/umjammer/vavi-image-filter-ml/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/umjammer/vavi-image-filter-ml/actions/workflows/codeql-analysis.yml)
![Java](https://img.shields.io/badge/Java-8-b07219)

# vavi-image-filter-ml

<img src="https://camo.githubusercontent.com/aeb4f612bd9b40d81c62fcbebd6db44a5d4344b8b962be0138817e18c9c06963/68747470733a2f2f7777772e74656e736f72666c6f772e6f72672f696d616765732f74665f6c6f676f5f686f72697a6f6e74616c2e706e67" width="320" />

BufferedImageOp filters using Machine Learning

## Install

https://jitpack.io/#umjammer/vavi-image-filter-ml

## Usage

```java
    AiasEsrGanSuperResolutionOp filter = new AiasEsrGanSuperResolutionOp();
    BufferedImage image = ImageIO.read(Files.newInputStream(in));
    BufferedImage filteredImage = filter.filter(image, null);
    ImageIO.write(filteredImage, "PNG", Files.newOutputStream(out));
```

## References

 * TensorFlow
   * https://github.com/tensorflow/java
   * https://github.com/mymagicpower/AIAS
 * CoreML
   * https://github.com/john-rocky/CoreML-Models

## TODO

 * tensorflow esrgan speed is not practical for ordinary comic images
 * aias maven repository