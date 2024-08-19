package com.cv.designvision.models;

import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements IUserDAO {
    List<IUser> users = new ArrayList<>();
    int incrementingId = 1;

//    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
//        throw (E) e;
//    }

    @Override
    public void createUser(IUser user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
//            sneakyThrow(
//                    new SQLiteException(
//                            "NOT NULL constraint failed: email or password",
//                            SQLiteErrorCode.SQLITE_CONSTRAINT_NOTNULL)
//            );
            throw new RuntimeException("Email or Password is null or empty");
        }

        user.setId(incrementingId++);
        users.add(user);
    }

    @Override
    public void updateUser(IUser user) {
    }

    @Override
    public void deleteUser(IUser user) {
        users.remove(user);
    }

    @Override
    public IUser readUser(int id) {
        for (IUser user : users) {
            if (user.getId() == id) return new User(user);
        }
        return null;
    }

    @Override
    public List<IUser> getAllUsers() {
        List<IUser> newUserList = new ArrayList<>();
        for (IUser user : users) {
            newUserList.add(new User(user));
        }
        return newUserList;
    }

    @Override
    public IUser readUserByEmailAndPassword(IUser user) {
        for (IUser candidate : users) {
            if (candidate.getEmail().equals(user.getEmail())) {
                if (candidate.getPassword().equals(user.getPassword())) {
                    return new User(candidate);
                }
            }
        }
        return null;
    }
}
