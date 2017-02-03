/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.handlers;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.nucleo.sessions.AbstractFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author generico
 * @param <T>
 */
public class GenericLazyDataModel<T extends EntidadeGenerica> extends LazyDataModel<T> {    
    protected AbstractFacade<T> facade;
    private List<T> itemList;
    private Boolean isCountValid = Boolean.FALSE;
    @Getter
    @Setter
    private Map<String, Object> permanentFilters;
    private Class<T> entityClass;

    public GenericLazyDataModel() {
        super();
        this.permanentFilters = null;
        this.facade = null;
        this.itemList = null;
    }

    public GenericLazyDataModel(AbstractFacade<T> facade) {
        this();
        this.facade = facade;
    }

    public GenericLazyDataModel(AbstractFacade<T> facade, Map<String, Object> filters) {
        this();
        this.facade = facade;
        this.permanentFilters = filters;
    }
   
    public GenericLazyDataModel(List<T> itemList) {
        this();
        this.itemList = itemList;
    }  
    
    public GenericLazyDataModel(Class<T> entityClass) {
        this();
        this.entityClass = entityClass;
    }    
    
    

    @Override
    public Object getRowKey(T object) {
        return Integer.toString(object.hashCode());
    }

    @Override
    public T getRowData(String rowKey) {
        if (itemList != null) {
            for (T entity : itemList) {
                if (getRowKey(entity).equals(rowKey)) {
                    return entity;
                }
            }
        }
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        // Turn sort info into a linked hash map for the facade
        HashMap<String, String> sortFields = new LinkedHashMap<>();
        Map<String, Object> _filters = new TreeMap<>();
        if (null != getPermanentFilters()) {
            _filters.putAll(getPermanentFilters());
        }
        if (null != filters) {
            _filters.putAll(filters);
        }
        if (multiSortMeta != null) {
            for (SortMeta s : multiSortMeta) {
                sortFields.put(s.getSortField(), s.getSortOrder().toString());
            }
        }

        itemList = findRange(new LazyQueryHandler(first, pageSize, sortFields, _filters));
        this.setRowCount(count(_filters)); // Count ALL records for the applied filter
        this.isCountValid = Boolean.TRUE;
        return itemList;        
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        Map<String, Object> _filters = new TreeMap<>();
        if (null != getPermanentFilters()) {
            _filters.putAll(getPermanentFilters());
        }
        if (null != filters) {
            _filters.putAll(filters);
        }

        if (this.facade != null) { // Handle data that needs to be retrieved from the data back-end of the application
            String sortOrderName = sortOrder.toString();

            this.itemList = findRange(new LazyQueryHandler(first, pageSize, sortField, sortOrderName, _filters));
            this.setRowCount(count(_filters)); // Count ALL records for the applied filter
            this.isCountValid = Boolean.TRUE;
            return this.itemList;
        } else if (this.itemList != null) { // Handle data that was passed in by application

            // filter
            List<T> filteredItemList = filter(this.itemList, _filters);

            // sort
            if (sortField != null) {
                Collections.sort(filteredItemList, new CustomLazyEntitySorter<>(sortField, sortOrder));
            }

            // rowCount
            int itemCount = filteredItemList.size();
            this.setRowCount(itemCount);

            // paginate
            if (itemCount > pageSize) {
                try {
                    return filteredItemList.subList(first, first + pageSize);
                } catch (IndexOutOfBoundsException e) {
                    return filteredItemList.subList(first, first + (itemCount % pageSize));
                }
            } else {
                return filteredItemList;
            }

        } else { // Nothing passed in by application
            return null;
        }
    }

    private List<T> filter(List<T> itemList, Map<String, Object> filters) {

        List<T> filteredItemList = new ArrayList<>();

        // apply filters
        for (T entity : itemList) {
            boolean match = true;
            for (String filterField : filters.keySet()) {
                String filterValue = String.valueOf(filters.get(filterField)).toLowerCase();
                String fieldValue = String.valueOf(EntidadeGenerica.getFieldValue(entity, filterField)).toLowerCase();
                if (filterValue != null && fieldValue != null && !fieldValue.startsWith(filterValue)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                filteredItemList.add(entity);
            }
        }
        return filteredItemList;
    }

    @Override
    public int getRowCount() {
        int rowCountTmp = super.getRowCount();
        if (this.isCountValid) {
            return rowCountTmp;
        } else {
            if (null == this.facade) {
                rowCountTmp = itemList.size();
            } else {
                rowCountTmp = this.facade.count();
            }
            setRowCount(rowCountTmp);
            this.isCountValid = Boolean.TRUE;
            return rowCountTmp;
        }
    }

    public void refreshQuery() {
        this.isCountValid = Boolean.FALSE;
    }

    protected List<T> findRange(LazyQueryHandler lazyQueryHandler) {
        return this.facade.findRange(lazyQueryHandler);
    }
    
    protected int count(Map<String, Object> filters){
        return this.facade.count(filters);
    }
}
