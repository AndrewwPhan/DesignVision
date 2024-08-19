package com.cv.designvision.Services;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    protected final List<IObserver> observers = new ArrayList<>();

    /**
     * Register an observer to be notified on change.
     * @param observer Observer to be notified of change.
     */
    public void register(IObserver observer){
        observers.add(observer);
    }

    /**
     * Un-Register an observer to no longer be notified of change.
     * @param observer Observer to be un-registered.
     */
    public void unregister(IObserver observer){
        observers.remove(observer);
    }

    /**
     * Notify all observers of state change.
     */
    protected abstract void notifyObservers();
}
