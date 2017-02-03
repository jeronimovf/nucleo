/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.handlers;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author j129-9
 */
@Getter
@Setter
public class LazyQueryHandler {

    private int paginationFirst;
    private int pageSize;
    private Map<String, String> sortFields;
    private Map<String, Object> filters;

    public LazyQueryHandler(int paginationFirst, int pageSize, Map<String, String> sortFields, Map<String, Object> filters) {
        this.paginationFirst = paginationFirst;
        this.pageSize = pageSize;
        this.sortFields = sortFields;
        this.filters = filters;
    }

    public LazyQueryHandler(int paginationFirst, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
        this.paginationFirst = paginationFirst;
        this.pageSize = pageSize;
        this.sortFields = new HashMap<>();
        if (null != sortField && !sortField.isEmpty()) {
            this.sortFields.put(sortField, sortOrder);
        }
        this.filters = filters;
    }

}
