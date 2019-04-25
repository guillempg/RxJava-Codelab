package com.jraska.rx.codelab;

import com.jraska.rx.codelab.furniture.Parts;
import com.jraska.rx.codelab.furniture.Screw;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;

public class Task4_ErrorHandling {
  Observable<Screw> screwsObservable;
  Observable<Screw> extraScrewsObservable;

  @Before
  public void before() {
    screwsObservable = Observable.fromIterable(Parts.fiveScrews())
      .concatWith(Observable.error(new RuntimeException("Damaged screw!")));

    extraScrewsObservable = Observable.fromIterable(Parts.fiveScrews());
  }

  @Test
  public void printErrorMessage() {
    // TODO: Print all values and incoming error message
    screwsObservable.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));

  }

  @Test
  public void emitCustomItemOnError() {
    // TODO: When an error happens, emit number artificial screw
    screwsObservable.onErrorReturnItem(Parts.defaultScrew()).subscribe(System.out::println);
  }

  @Test
  public void subscribeToExtraObservableOnError() {
    // TODO: When an error happens, subscribe to extra observable
    screwsObservable.onErrorResumeNext(extraScrewsObservable).subscribe(System.out::println);
  }

  @Test
  public void retryOnError() {
    Observable<Screw> flakeyObservable = Parts.flakeyScrew();
    // TODO: Observable is a bit flakey and often fails, use retry to make it always complete

    for (int i = 0; i < 10; i++) {
      flakeyObservable.subscribe(System.out::println);
    }
  }
}
