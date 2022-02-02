package com.varun.concurrency.ch06.renderer;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.varun.concurrency.Helper.launderThrowable;
import static com.varun.concurrency.Helper.renderImage;
import static com.varun.concurrency.Helper.renderText;
import static com.varun.concurrency.Helper.scanForImageInfo;

/*
 * Renderer uses a CompletionService to download each image and render it as a single task. Now the list of these
 * tasks can be run in parallel which turns the system into homogeneous tasks running parallel.
 * */
public class Renderer {
  private final ExecutorService executorService;

  public Renderer(ExecutorService executorService) {
    this.executorService = executorService;
  }

  void renderPage(CharSequence source) {
    final List<ImageInfo> info = scanForImageInfo(source);
    CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
    for (final ImageInfo imageInfo : info) {
      completionService.submit(imageInfo::downloadImage);
      renderText(source);
      try {
        for (int i = 0, n = info.size(); i < n; i++) {
          Future<ImageData> future = completionService.take();
          ImageData imageData = future.get();
          renderImage(imageData);
        }
      } catch (ExecutionException e) {
        throw launderThrowable(e);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
