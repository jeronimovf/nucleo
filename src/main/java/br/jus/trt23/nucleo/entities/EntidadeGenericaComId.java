/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.entities;

import br.jus.trt23.nucleo.interfaces.Entidade;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author j129-9
 */
@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class EntidadeGenericaComId implements Serializable, Comparable, Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected static String[] uniqueIndex;
    
    public abstract String getNomeNatural();

    public static Object getFieldValue(Object bean, String fieldName) {
        try {
            BeanInfo info = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (pd.getName().equals(fieldName)) {
                    return pd.getReadMethod().invoke(bean);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(EntidadeGenericaComId.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        //uma instância não pode ser igual a nulo
        if (obj == null) {
            return false;
        }

        //a mesma instância com dois ponteiros é igual
        if (this == obj) {
            return true;
        }

        //se os objetos não são da mesma classe não são iguais
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        EntidadeGenericaComId eg = (EntidadeGenericaComId) obj;

        //se ambos os objetos são persistentes a igualdade pode ser testada
        //pelos ids
        if (null != getId()) {
            return getId().equals(eg.getId());
        }

        //o objeto corrente não é persistente, mas o testado é.  não são iguais.
        if (null != eg.getId()) {
            return false;
        }

        //ambos os objetos não são persistentes.  igualdade verificada
        //pelos campos de indice unico.
        return compareByIndex(eg);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        try {
            hash = 53 * hash + Objects.hashCode(this.id);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, String.format("Error {0} on hashCode method of {1}", e.getMessage()), new Object[]{this, this.getClass().getName()});
        }
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        try {
            if (null == o) {
                return -1;
            }
            if (o instanceof EntidadeGenericaComId) {
                EntidadeGenericaComId eg = (EntidadeGenericaComId) o;
                if (null == getId()) {
                    return -1;
                }
                return getId().compareTo(eg.getId());
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    private boolean compareByIndex(EntidadeGenericaComId eg) {
        Class c = this.getClass();
        Field f1;
        Object o1, o2;
        for (String fieldName : uniqueIndex) {
            try {
                f1 = c.getField(fieldName);
                o1 = f1.get(this);
                o2 = f1.get(eg);
                if (!o1.equals(o2)) {
                    return false;
                }

            } catch (NoSuchFieldException | SecurityException |
                    IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(EntidadeGenericaComId.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
}
