package com.framework.token.captcha;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 * 图片验证码  无图片样式
 */
public class NoGimpy extends Configurable implements GimpyEngine {

    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {
        BufferedImage distortedImage = new
                BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();

        graph.drawImage(bufferedImage, 0, 0, null, null);

        graph.dispose();

        NoiseProducer noiseProducer = this.getConfig().getNoiseImpl();
//        noiseProducer.makeNoise(distortedImage, 0.1F, 0.1F, 0.25F, 0.25F);
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.25F, 0.5F, 0.9F);

        return distortedImage;
    }
}
