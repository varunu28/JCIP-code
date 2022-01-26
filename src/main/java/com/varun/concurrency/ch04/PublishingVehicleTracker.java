package com.varun.concurrency.ch04;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;

/*
 * PublishingVehicleTracker is thread-safe as it leverages ConcurrentHashMap for ensuring that
 * any modifications to data structure is thread-safe. Even though it publishes ThreadSafePoint
 * which is no longer final as compared to Point class, it is itself thread-safe as it uses
 * Java monitor for any modifications.
 *
 * Therefore, overall the PublishingVehicleTracker is thread safe.
 * */
@ThreadSafe
public class PublishingVehicleTracker {
  private final ConcurrentMap<String, ThreadSafePoint> locations;

  public PublishingVehicleTracker(Map<String, ThreadSafePoint> locations) {
    this.locations = new ConcurrentHashMap<>(locations);
  }

  public Map<String, ThreadSafePoint> getLocations() {
    return Collections.unmodifiableMap(this.locations);
  }

  public ThreadSafePoint getLocation(String id) {
    return this.locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (!this.locations.containsKey(id)) {
      throw new IllegalArgumentException("No such ID: " + id);
    }
    this.locations.get(id).set(x, y);
  }
}
