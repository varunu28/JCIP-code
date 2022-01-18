package com.varun.concurrency.ch03;

/*
 * InheritableThreadLocal allows to share the value that is set to the child threads. In below
 * example, we are unable to retrieve the value for threadLocal that was set as part of parent
 * thread. But we are able to retrieve the value that was set as part of InheritableThreadLocal by
 * the parent.
 * */
public class InheritableThreadLocalDemo {

  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    new Thread(
            () -> {
              System.out.println("In parent thread");
              threadLocal.set("Parent thread: ThreadLocal");
              inheritableThreadLocal.set("Parent thread: InheritableThreadLocal");

              System.out.println(threadLocal.get());
              System.out.println(inheritableThreadLocal.get());

              new Thread(
                      () -> {
                        System.out.println("In child thread");
                        System.out.println(threadLocal.get());
                        System.out.println(inheritableThreadLocal.get());
                      })
                  .start();
            })
        .start();
  }
}
