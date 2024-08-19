package com.cv.designvision.Services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a generic data list with additional functionalities for
 * managing an active index within the list. It extends the {@link Iterable}
 * interface to allow iteration over the elements.
 *
 * @param <T> The type of elements this list can hold.
 */
public class UserDataList<T> implements Iterable<T> {

    private final List<T> items = new LinkedList<>();
    private int idx = -1;

    /**
     * Returns an iterator over the elements in this list.
     *
     * @return An iterator object for traversing the list.
     */
    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return The size of the list.
     */
    public int size() {
        return items.size();
    }

    /**
     * Adds an element to the end of the list and sets the active index to
     * the newly added element.
     *
     * @param element The element to be added.
     */
    public void add(T element) {
        items.add(element);
        setActiveIndex(size() - 1);
    }

    /**
     * Adds all elements from the provided iterator to this list and sets the
     * active index to the first element.
     *
     * @param items An iterator containing the elements to be added.
     */
    public void addAll(Iterator<T> items) {
        while (items.hasNext()){
            add(items.next());
        }
        idx = 0;
    }

    /**
     * Removes the element at the active index from the list. If the list is
     * empty, this method does nothing. After removal, the active index is
     * adjusted to point to the element after the removed one (if any).
     */
    public void remove() {
        if (items.isEmpty()) return;
        items.remove(idx);
        setActiveIndex(idx);
    }

    /**
     * Removes the element at the specified index from the list. If the
     * provided index is out of bounds, no removal happens. The active index
     * is adjusted based on the removed element's position relative to the
     * current active index.
     *
     * @param i The index of the element to be removed.
     */
    public void removeAtIndex(int i) {
        items.remove(i);
        if (i < idx) setActiveIndex(idx - 1);
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     * If the element is not found, no removal happens. The active index
     * is adjusted based on the removed element's position relative to the
     * current active index.
     *
     * @param item The element to be removed.
     */
    public void remove(T item) {
        removeAtIndex(items.indexOf(item));
    }

    /**
     * Removes all elements from the list and sets the active index to -1.
     */
    public void clear() {
        items.clear();
        idx = -1;
    }

    /**
     * Sets the active index to the specified position within the list. The
     * index is clamped to the valid range (between 0 and end).
     * If the list is empty, the active index is set to -1.
     *
     * @param i The desired index to set as active.
     */
    public void setActiveIndex(int i) {
        if (items.isEmpty()) idx = -1;
        idx = Math.max(Math.min(i, size() - 1), 0);
    }

    /**
     * Sets the active index to the first occurrence of the specified element
     * in the list. If the element is not found, the active index is set to
     * the end of the list.
     *
     * @param item The element to set as active.
     */
    public void setActive(T item) {
        if (items.isEmpty()) idx = -1;
        idx = items.indexOf(item);
        // set to end if not found
        if (idx < 0) idx = size() - 1;
    }

    /**
     * Moves the active index to the next element in the list (if any) and
     * returns the element at the new active index.
     *
     * @return The element at the new active index.
     */
    public T next() {
        setActiveIndex(idx + 1);
        return items.get(idx);
    }

    /**
     * Moves the active index to the previous element in the list (if any) and
     * returns the element at the new active index.
     *
     * @return The element at the new active index.
     */
    public T prev() {
        setActiveIndex(idx - 1);
        return items.get(idx);
    }

    /**
     * Returns the element at the current active index. If the list is empty,
     * this method returns null.
     *
     * @return The element at the active index or null if the list is empty.
     */
    public T getActive() {
        if (items.isEmpty()) return null;
        return items.get(idx);
    }

    /**
     * Returns the current active index within the list.
     *
     * @return The index of the active element.
     */
    public int getActiveIndex(){
        return idx;
    }

    /**
     * Returns a string representation of this list.
     *
     * @return A string representation of the list.
     */
    @Override
    public String toString(){
        return items.toString();
    }
}
