package com.gitlab.ettoreleandrotognoli.storage.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractLocalRepository<E> {

    public static class Event<E> {

        private final AbstractLocalRepository<E> source;
        private final Stream<File> changedFiles;

        public Event(AbstractLocalRepository<E> source, Stream<File> changedFiles) {
            this.source = source;
            this.changedFiles = changedFiles;
        }

        public AbstractLocalRepository<E> getSource() {
            return source;
        }

        public Stream<File> getChangedFiles() {
            return changedFiles;
        }
    }

    public interface Listener<E> {
        void dataChanged(Event<E> event) throws Exception;
    }

    private List<Listener<E>> listeners = new ArrayList<>();


    public void addListener(Listener<E> listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener<E> listener) {
        this.listeners.remove(listener);
    }

    protected void fireDataChanged(Stream<File> changedFiles) throws Exception{
        Event<E> event = new Event<>(this, changedFiles);
        for (Listener<E> listener : listeners) {
            listener.dataChanged(event);
        }
    }

}
