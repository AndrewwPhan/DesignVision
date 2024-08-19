package com.cv.designvision.Services;

public interface IObserver {
    /**
     * Update method for what a registered observer wants to do
     * with updated state notification.
     * @param value Value to be updated
     * @param <T> Type of value
     */
    <T> void update(T value);
}
