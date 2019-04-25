package com.jraska.rx.codelab;

import com.jraska.rx.codelab.forest.Forest;
import com.jraska.rx.codelab.forest.Log;
import com.jraska.rx.codelab.forest.Lumberjack;
import com.jraska.rx.codelab.forest.Tools;
import com.jraska.rx.codelab.furniture.*;
import io.reactivex.Observable;
import org.junit.Test;

import javax.swing.text.Caret;
import java.util.List;

public class Task3_CombiningToAssembleFurniture {
  @Test
  public void zip_doSomeChair() {
    Observable<Log> logObservable = Lumberjack.cut(Forest.AMAZON).map(Tools::handSaw);

    // TODO: Carpenter wants to do some chairs, he can get some box of screws from Parts and he needs Logs of wood
    Observable<List<Screw>> screws = Parts.boxOfTenScrews().buffer(Carpenter.SCREWS_FOR_CHAIR);
    Observable<Chair> chairObservable = logObservable.zipWith(screws,Carpenter::chair);
    chairObservable.subscribe(System.out::println);
  }

  @Test
  public void concatWith_doTableNow() {
    Observable<Log> logObservable = Lumberjack.cut(Forest.AMAZON).map(Tools::handSaw);

    // TODO: We now need to  create Table, but one Box of screws is not enough, we can concatWith two boxes to have enough screws
    Observable<List<Screw>> screws = Parts.boxOfTenScrews()
                                          .concatWith(Parts.boxOfTenScrews())
                                          .buffer(Carpenter.SCREWS_FOR_TABLE);
    Observable<Table> tableObservable = logObservable.zipWith(screws, Carpenter::table);
    tableObservable.subscribe(System.out::println);
  }

  @Test
  public void startWith_doAnotherTable() {
    Observable<Log> logObservable = Lumberjack.cut(Forest.AMAZON).map(Tools::handSaw);

    // TODO: We can achieve the same with just putting twice fiveScrews from Parts at the start of the Screws Observable
    Observable<List<Screw>> screws = Parts.boxOfTenScrews()
                                          .startWith(Parts.fiveScrews())
                                          .startWith(Parts.fiveScrews())
                                          .buffer(Carpenter.SCREWS_FOR_TABLE);

    Observable<Table> tableObservable = logObservable.zipWith(screws,Carpenter::table);
    tableObservable.subscribe(System.out::println);
  }

  @Test
  public void flatMapZip_makeSomeSofa() {
    Observable<Log> logObservable = Lumberjack.cut(Forest.AMAZON).map(Tools::handSaw);

    // TODO: Now Carpenter needs some Rivets to do Sofa, he can use flatMap with Parts.rivet to get some rivets needed for Sofas
    Observable<List<Rivet>> rivet = Parts.boxOfTenScrews()
      .concatWith(Parts.boxOfTenScrews())
      .flatMap(Parts::rivet)
      .buffer(Carpenter.RIVETS_FOR_SOFA);
    Observable<Sofa> sofaObservable = logObservable.zipWith(rivet,Carpenter::sofa);

  sofaObservable.subscribe(System.out::println);
  }
}
