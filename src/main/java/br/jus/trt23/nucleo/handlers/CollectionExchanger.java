/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.handlers;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author j129-9
 * @param <T>
 */
public class CollectionExchanger<T extends Collection> {

    @Getter
    @Setter
    private T sourceCollection;
    @Getter
    @Setter
    private T destinationCollection;
    @Getter
    @Setter
    private T sourceCollectionSelected;
    @Getter
    @Setter
    private T destinationCollectionSelected;
    private Class<T> collectionClass;

    public CollectionExchanger(T sourceCollection, T destinationCollection) {
        this((Class<T>) sourceCollection.getClass());
        this.sourceCollection = sourceCollection;
        this.destinationCollection = destinationCollection;
    }

    public CollectionExchanger(T sourceCollection) {
        this((Class<T>) sourceCollection.getClass());
        this.sourceCollection = sourceCollection;
    }

    public CollectionExchanger(Class<T> collectionClass) {
        this.collectionClass = collectionClass;
        try {
            this.sourceCollectionSelected = (T) collectionClass.newInstance();
            this.destinationCollectionSelected = (T) collectionClass.newInstance();
            this.sourceCollection = (T) collectionClass.newInstance();
            this.destinationCollection = (T) collectionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CollectionExchanger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Boolean addAllAllowed() {
        return sourceCollection.size() > 0;
    }

    public Boolean addSelectedAllowed() {
        return sourceCollectionSelected.size() > 0;
    }

    public Boolean removeAllAllowed() {
        return destinationCollection.size() > 0;
    }

    public Boolean removeSelectedAllowed() {
        return destinationCollectionSelected.size() > 0;
    }

    public void addAll() {
        destinationCollection.addAll(sourceCollection);
        sourceCollection.clear();
    }

    public void addSelected() {
        destinationCollection.addAll(sourceCollectionSelected);
        sourceCollection.removeAll(sourceCollectionSelected);
        sourceCollectionSelected.clear();
    }

    public void removeAll() {
        sourceCollection.addAll(destinationCollection);
        destinationCollection.clear();
    }

    public void removeSelected() {
        sourceCollection.addAll(destinationCollectionSelected);
        destinationCollection.removeAll(destinationCollectionSelected);
        destinationCollectionSelected.clear();
    }

}
