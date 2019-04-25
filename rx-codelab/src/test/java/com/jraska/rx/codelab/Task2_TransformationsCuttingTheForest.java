package com.jraska.rx.codelab;

import com.jraska.rx.codelab.forest.BasketOfWood;
import com.jraska.rx.codelab.forest.Fireplace;
import com.jraska.rx.codelab.forest.Firewood;
import com.jraska.rx.codelab.forest.Forest;
import com.jraska.rx.codelab.forest.Log;
import com.jraska.rx.codelab.forest.Lumberjack;
import com.jraska.rx.codelab.forest.Tools;
import com.jraska.rx.codelab.forest.Tree;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Task2_TransformationsCuttingTheForest {
  Fireplace fireplace;

  @Before
  public void before() {
    fireplace = new Fireplace();
  }

  @Test
  public void map_fromOnePieceExactlyToOnePieceOfOtherStuff() {
    Observable<Tree> treeObservable = Lumberjack.cut(Forest.AMAZON);
    // TODO: Transform Observable of Trees to Observable of Firewood. Tools like handSaw and chopping can be useful
    Observable<Firewood> woodObservable = treeObservable.map(Tools::handSaw)
      .map(Tools::chop);
    fireplace.subscribeForBurn(woodObservable);
  }

  @Test
  public void flatMap_chainSawProducesMoreLogs() {
    Observable<Tree> treeObservable = Lumberjack.cut(Forest.AMAZON);
    // TODO:  Cutting wood by handSaw is not effective, lets use chainSaw now
    Observable<Firewood> woodObservable = treeObservable.flatMap(Tools::chainSaw)
      .map(Tools::chop);
    fireplace.subscribeForBurn(woodObservable);
  }

  @Test
  public void buffer_createBasketsOfWood() {
    // TODO: We want some bigger fire, lets put firewood into baskets. Buffering int BasketOfWood might be useful
    Observable<Tree> treeObservable = Lumberjack.cut(Forest.AMAZON);
    Observable<BasketOfWood> basketsObservable = treeObservable
      .flatMap(Tools::chainSaw)
      .map(Tools::chop)
      .buffer(BasketOfWood.BASKET_SIZE)
      .map(BasketOfWood::new);
    fireplace.subscribeForBasketBurn(basketsObservable);
  }

  @Test
  public void flatMap_buffer_createBasketsOfWoodWithCuttingMachine() {
    // TODO: Lets be even more effective and use machineChop tool to get
    Observable<Tree> treeObservable = Lumberjack.cut(Forest.AMAZON);

    Observable<BasketOfWood> basketsObservable = treeObservable.flatMap(Tools::chainSaw)
      .flatMap(Tools::machineChop)
      .buffer(BasketOfWood.BASKET_SIZE)
      .map(BasketOfWood::new);
  fireplace.subscribeForBasketBurn(basketsObservable);
  }

  @Test
  public void debounce_filterTooMuchWoodAtOnce() {
    Observable<Log> logsObservable = Lumberjack.cut(Forest.AMAZON)
      .flatMap((tree) -> {
        Thread.sleep(tree.treeOrdinal() * 100);
        return Tools.chainSaw(tree);
      });

    // TODO: Fireplace is able to handle only one firewood per 250 milliseconds, use debounce for inputs to filter too much firewood out
    Observable<Firewood> woodObservable = logsObservable.map(Tools::chop).debounce(250, TimeUnit.MILLISECONDS);
    fireplace.subscribeForBurn(woodObservable);
  }
}
