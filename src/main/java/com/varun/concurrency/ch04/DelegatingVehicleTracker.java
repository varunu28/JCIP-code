package com.varun.concurrency.ch04;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;

/*
 * We moved away from MonitorVehicleTracker which holds all the responsibility to make the class
 * thread-safe on its own to DelegatingVehicleTracker which uses underlying thread-safe mechanisms
 * to build a thread-safe class.
 *
 * We replace MutablePoint with Point which stores all the state variables as final. So even if we
 * share the Point instances across threads, it won't be an issue as it cannot be mutated.
 *
 * We also updated our HashMap with a ConcurrentHashMap with which we no longer require our methods
 * to use synchronized and use the Java monitor to lock all the methods. ConcurrentHashMap internally
 * does this for us.
 *
 * We also don't perform a deepCopy while returning the locations as part of getLocations(). It is
 * because we are returning an unmodifiableCopy of locations which prevent the threads from updating
 * the map data structure. The elements inside the map are Point which in itself is thread-safe.
 * */
@ThreadSafe
public class DelegatingVehicleTracker {
  private final ConcurrentMap<String, Point> locations;

  public DelegatingVehicleTracker(Map<String, Point> points) {
    this.locations = new ConcurrentHashMap(points);
  }

  public Map<String, Point> getLocations() {
    return Collections.unmodifiableMap(this.locations);
  }

  public Point getLocation(String id) {
    return this.locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (locations.replace(id, new Point(x, y)) == null) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
  }
}
