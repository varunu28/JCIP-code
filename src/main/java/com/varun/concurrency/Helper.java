package com.varun.concurrency;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class Helper {

  public static BigInteger extractFromRequest(String request) {
    return null;
  }

  public static BigInteger[] factor(BigInteger i) {
    return null;
  }

  public static void encodeIntoResponse(String response, BigInteger[] factors) {}

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
}
