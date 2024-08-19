package com.cv.designvision.utils;

import com.cv.designvision.Services.UserDataList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataListTest {

    @Test
    void addingItem_increasesSizeOfList() {
        UserDataList<Integer> list = new UserDataList<>();

        list.add(1);

        assertThat(list.size()).isOne();
    }

    @Test
    void addingItem_setsItAsActive() {
        UserDataList<Integer> list = new UserDataList<>();

        list.add(1);
        list.add(2);

        assertThat(list.getActive()).isEqualTo(2);
    }

    @Test
    void clearingAll_nullsActive() {
        UserDataList<Integer> list = new UserDataList<>();
        list.add(1);

        list.clear();

        assertThat(list).isEmpty();
        assertThat(list.getActive()).isNull();
    }

    @Test
    void setActiveItem_setsActiveByIndex() {
        UserDataList<Integer> list = new UserDataList<>();

        for (int i = 1; i <= 10; ++i) list.add(i);
        list.setActiveIndex(4);

        assertThat(list.getActive()).isEqualTo(5);
    }

    @Test
    void nextItem_getsNextInList() {
        UserDataList<Integer> list = new UserDataList<>();

        list.add(1);
        list.add(2);
        list.setActiveIndex(0);

        assertThat(list.next()).isEqualTo(2);
        assertThat(list.getActive()).isEqualTo(2);
    }

    @Test
    void prevItem_getsPreviousInList() {
        UserDataList<Integer> list = new UserDataList<>();

        list.add(1);
        list.add(2);

        assertThat(list.prev()).isEqualTo(1);
        assertThat(list.getActive()).isEqualTo(1);
    }

    @Test
    void removingFirstItem_setsNewFirstAsActive() {
        UserDataList<Integer> list = new UserDataList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        list.setActiveIndex(0);
        list.remove();

        assertThat(list.getActive()).isEqualTo(2);
    }

    @Test
    void removingMiddleItem_keepsActiveSame() {
        UserDataList<Integer> list = new UserDataList<>();
        for (int i = 1; i <= 10; ++i) list.add(i);

        list.setActive(8);
        list.remove(5);
        assertThat(list.getActive()).isEqualTo(8);

        list.setActive(3);
        list.remove(6);
        assertThat(list.getActive()).isEqualTo(3);
    }

    @Test
    void setActiveByItem_setsToEndIfItemNotFound() {
        UserDataList<Integer> list = new UserDataList<>();
        for (int i = 1; i <= 10; ++i) list.add(i);

        list.setActive(1);
        assertThat(list.getActive()).isEqualTo(1);
        list.setActive(50);
        assertThat(list.getActive()).isEqualTo(10);
    }

    @Test
    void removingLastItem_nullsActive() {
        UserDataList<Integer> list = new UserDataList<>();
        list.add(1);

        list.remove();

        assertThat(list.getActive()).isNull();
    }

    @Test
    void addAll_addsAllItemsFromOtherIterator() {
        UserDataList<Integer> other = new UserDataList<>();
        int count = 5;
        for (int i = 1; i <= count; ++i) other.add(i);

        UserDataList<Integer> list = new UserDataList<>();
        list.addAll(other.iterator());

        assertThat(list.size()).isEqualTo(count);
    }

    @Test
    void addAll_setsActiveBackToFirstItem() {
        UserDataList<Integer> other = new UserDataList<>();
        int count = 5;
        for (int i = 1; i <= count; ++i) other.add(i);

        UserDataList<Integer> list = new UserDataList<>();
        list.addAll(other.iterator());

        assertThat(list.getActive()).isEqualTo(1);
    }

    @Test
    void removeFormEndSetsActiveToNewEnd() {
        UserDataList<Integer> list = new UserDataList<>();
        int count = 5;
        for (int i = 0; i <= count; i++) list.add(i);

        list.remove();

        assertThat(list.getActive()).isEqualTo(count-1);
    }
}