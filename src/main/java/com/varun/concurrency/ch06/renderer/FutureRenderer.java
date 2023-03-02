package com.varun.concurrency.ch06.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.varun.concurrency.Helper.*;

/*
 * As part of FutureRenderer we parallelize the download task and renderer task using Callable. Using Callable helps
 * as we can initiate the task and then retrieve the value from the task later. Doing this gives us a head-start for
 * downloading the images.
 *
 * But at the same time both download and render are different tasks altogether and any of them can become a
 * bottleneck even if we are running them in parallel. We can face a scenario where render is 10 times slower than
 * download and by running both tasks in parallel doesn't improve the performance of our application by much.
 *
 * Real performance improvement comes from dividing work into tasks where a large number of independent homogeneous
 * tasks are run in parallel to each other.
 * */
public class FutureRenderer {
    private static final Integer NTHREADS = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS);

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<>();
            for (ImageInfo imageInfo : imageInfos) {
                result.add(imageInfo.downloadImage());
            }
            return result;
        };
        Future<List<ImageData>> future = executorService.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (ExecutionException e) {
            throw launderThrowable(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        }
    }
}
