package com.varun.concurrency.ch06.renderer;

import java.util.ArrayList;
import java.util.List;

import static com.varun.concurrency.Helper.*;

/*
 * Example of sequential page renderer
 * */
public class SingleThreadRenderer {

    public void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data : imageData) {
            renderImage(data);
        }
    }
}
