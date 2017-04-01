package com.hb.lovebaby.language;

import java.util.Observable;
import java.util.Observer;

public class SwitchLanguageObserver implements Observer {

    private ObserverChange mObserverChange;

    public SwitchLanguageObserver(Observable simpleObservable) {
        simpleObservable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (mObserverChange != null) mObserverChange.observerChange();
    }

    public void setOnObserverChange(ObserverChange observerChange) {
        this.mObserverChange = observerChange;
    }

    public interface ObserverChange {

        public void observerChange();
    }

}
