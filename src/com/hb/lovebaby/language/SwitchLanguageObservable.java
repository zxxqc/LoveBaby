package com.hb.lovebaby.language;

import java.util.Observable;


public class SwitchLanguageObservable extends Observable {
    
    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
    
    @Override
    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }

}
