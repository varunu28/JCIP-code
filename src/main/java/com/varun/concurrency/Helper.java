package com.varun.concurrency;

import com.varun.concurrency.ch06.renderer.ImageData;
import com.varun.concurrency.ch06.renderer.ImageInfo;

import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper {

    public static BigInteger extractFromRequest(String request) {
        return null;
    }

    public static BigInteger[] factor(BigInteger i) {
        return null;
    }

    public static void encodeIntoResponse(String response, BigInteger[] factors) {
    }

    public static Date getBirthDateFromDb(int userId) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        return today.getTime();
    }

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }

    public static void handleRequest(Socket connection) {
        System.out.println("Handling connection: " + connection);
    }

    public static void renderText(CharSequence source) {
        System.out.println("Rendering source" + source);
    }

    public static List<ImageInfo> scanForImageInfo(CharSequence source) {
        return new ArrayList<>();
    }

    public static void renderImage(ImageData imageData) {
        System.out.println("Rendering image");
    }

    public static void processBuffer(byte[] bytes, int count) {
        System.out.println("Processing buffer");
    }
}
