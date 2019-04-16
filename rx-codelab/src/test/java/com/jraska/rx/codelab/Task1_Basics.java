package com.jraska.rx.codelab;

import org.junit.Test;
import io.reactivex.Observable;

public class Task1_Basics {
  @Test
  public void dummyObservable() {
    // TODO:  Create Observable with single String value, subscribe to it and print it to console (Observable.just)
    Observable<String> o = Observable.just("Hello");
    o.subscribe(System.out::println);
  }

  @Test
  public void arrayObservable() {
    // TODO:  Create Observable with ints 1, 2, 3, 4, 5, subscribe to it and print each value to console (Observable.fromArray)
    Observable<Integer> o = Observable.just(1,2,3,4,5);
    o.subscribe(System.out::println);
  }

  @Test
  public void helloOperator() {
    // TODO:  Create Observable with ints 1 .. 10 subscribe to it and print only odd values (Observable.range, observable.filter)
    Observable<Integer> o = Observable.range(1,10);
    o.filter(s -> s%2 != 0).subscribe(System.out::println);
  }

  @Test
  public void receivingError() {
    // TODO:  Create Observable which emits an error and print the console (Observable.error), subscribe with onError handling
    Observable<Integer> o = Observable.error(new IllegalArgumentException("wrong argument!"));
    o.subscribe(System.out::println,System.err::println);
  }
}
