package sk.stefan.listeners;

import java.util.function.Consumer;

/**
 * Listener pro odstranění entity.
 * @author elopin on 13.11.2015.
 */
@FunctionalInterface
public interface RemoveListener<T> extends Consumer {

    default void remove(T entity) {
        accept(entity);
    }
}
