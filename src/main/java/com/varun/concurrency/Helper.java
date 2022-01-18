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
}
