package br.jus.trt23.nucleo.handlers;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @param <T>
 */
public class CustomLazyEntitySorter<T> implements Comparator<T> {

    private final String sortField;
    private final SortOrder sortOrder;

    /**
     *
     * @param sortField
     * @param sortOrder
     */
    public CustomLazyEntitySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(T entity1, T entity2) {
        try {
            Object value1 = EntidadeGenerica.getFieldValue(entity1, sortField);
            Object value2 = EntidadeGenerica.getFieldValue(entity2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (SecurityException | IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }

}
