package com.cv.designvision.Services;

import com.cv.designvision.models.IUser;

public class MockObserver implements IObserver {
    private IUser observed;

    @Override
    public <T> void update(T value) {
        observed = (IUser)value;
    }

    public IUser getObserved() {
        return observed;
    }
}
