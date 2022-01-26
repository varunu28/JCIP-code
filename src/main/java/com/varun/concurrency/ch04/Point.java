package com.varun.concurrency.ch04;

import net.jcip.annotations.Immutable;

@Immutable
public class Point {

  public final int x;
  public final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
