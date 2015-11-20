package sk.stefan.listeners;

import java.util.function.Consumer;

/**
 * Listener pro události uložení entity.
 * @author elopin on 13.11.2015.
 */
@FunctionalInterface
public interface SaveListener<T> extends Consumer {

    default void save(T entity) {
        accept(entity);
    }
}
