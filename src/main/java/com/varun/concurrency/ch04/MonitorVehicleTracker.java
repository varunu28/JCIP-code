package com.varun.concurrency.ch04;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * MonitorVehicleTracker is thread safe as we use monitor pattern to acquire locks for all methods
 * & return a deep copy of locations which don't expose the state of class in a mutable form.
 *
 * In this case MonitorVehicleTracker class is responsible for ensuring thread safety and implement
 * thread safety mechanism as part of class itself using Java monitor pattern.
 * */
@ThreadSafe
public class MonitorVehicleTracker {
  @GuardedBy("this")
  private final Map<String, MutablePoint> locations;

  public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
    this.locations = deepCopy(locations);
  }

  private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
    Map<String, MutablePoint> result = new HashMap<>();
    for (String id : m.keySet()) {
      result.put(id, new MutablePoint(m.get(id)));
    }
    return Collections.unmodifiableMap(result);
  }

  public synchronized Map<String, MutablePoint> getLocations() {
    return deepCopy(this.locations);
  }

  public synchronized Optional<MutablePoint> getLocation(String id) {
    MutablePoint loc = this.locations.get(id);
    return Optional.of(loc == null ? null : new MutablePoint(loc));
  }

  public synchronized void setLocation(String id, int x, int y) {
    MutablePoint loc = this.locations.get(id);
    if (loc == null) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
    loc.x = x;
    loc.y = y;
  }
}
