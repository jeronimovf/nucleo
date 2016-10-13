/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.session;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author j129-9
 * @param <T>
 */
public abstract class AbstractFacadeComId<T extends EntidadeGenericaComId> {

    private final Class<T> entityClass;

    public AbstractFacadeComId(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext
    @Getter
    @Setter
    private EntityManager entityManager;

    public abstract List<T> complete(final String criteria);

    public void create(T entity) throws Exception {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        entity = getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    //o método findAll retorna apenas os registros que não tenham sido destruídos
    public List<T> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    //o método findAll retorna apenas os registros que não tenham sido destruídos
    public List<T> findAll(final Map<String, String> sortFields) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> c = cq.from(entityClass);
        if (sortFields != null && !sortFields.isEmpty()) {
            for (String sortField : sortFields.keySet()) {
                if (c.get(sortField) != null) {
                    String sortOrder = sortFields.get(sortField);
                    if (sortOrder.startsWith("ASC")) {
                        cq.orderBy(cb.asc(c.get(sortField)));
                    }
                    if (sortOrder.startsWith("DESC")) {
                        cq.orderBy(cb.desc(c.get(sortField)));
                    }
                }
            }
        }
        cq.select(c);
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    //o método findRange retorna apenas os registros que não tenham sido 
    //destruídos paginados entre range[0] e range[1]
    public List<T> findRange(int[] range) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    //o método findAll retorna apenas os registros que não tenham sido destruídos
    // e que estejam vigentes em todo o período informado
    public List<T> findAll(LocalDate vigenteDesde, LocalDate vigenteAte, final Map<String, String> sortFields) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c).where(
                cb.and(
                        cb.lessThanOrEqualTo(c.get("vigenteDesde"), vigenteDesde),
                        cb.or(
                                cb.isNull(c.get("vigenteDesde")),
                                cb.greaterThanOrEqualTo(c.get("vigenteDesde"), vigenteAte)
                        ),
                        cb.isNull(c.get("destruidoEm"))
                )
        );
        if (sortFields != null && !sortFields.isEmpty()) {
            for (String sortField : sortFields.keySet()) {
                if (c.get(sortField) != null) {
                    String sortOrder = sortFields.get(sortField);
                    if (sortOrder.startsWith("ASC")) {
                        cq.orderBy(cb.asc(c.get(sortField)));
                    }
                    if (sortOrder.startsWith("DESC")) {
                        cq.orderBy(cb.desc(c.get(sortField)));
                    }
                }
            }
        }
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    //o método findAll retorna apenas os registros que não tenham sido destruídos
    //o método findRange retorna apenas os registros que não tenham sido 
    //destruídos e que estejam vigentes em todo o período informado paginados
    //entre range[0] e range[1]
    public List<T> findRange(int[] range, LocalDate vigenteDesde,
            LocalDate vigenteAte) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c).where(
                cb.and(
                        cb.lessThanOrEqualTo(c.get("vigenteDesde"), vigenteDesde),
                        cb.or(
                                cb.isNull(c.get("vigenteDesde")),
                                cb.greaterThanOrEqualTo(c.get("vigenteDesde"), vigenteAte)
                        ),
                        cb.isNull(c.get("destruidoEm"))
                ));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public T newInstance() {
        try {
            return (T) Class.forName(entityClass.getName()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            return null;
        }
    }

    public List<T> findRange(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(entityRoot);
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        if (sortField != null && sortField.length() > 0) {
            if (entityRoot.get(sortField) != null) {
                if (sortOrder.startsWith("ASC")) {
                    cq.orderBy(cb.asc(entityRoot.get(sortField)));
                }
                if (sortOrder.startsWith("DESC")) {
                    cq.orderBy(cb.desc(entityRoot.get(sortField)));
                }
            }
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public List<T> findRange(int first, int pageSize, Map<String, String> sortFields, Map<String, Object> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(entityRoot);
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        if (sortFields != null && !sortFields.isEmpty()) {
            for (String sortField : sortFields.keySet()) {
                if (entityRoot.get(sortField) != null) {
                    String sortOrder = sortFields.get(sortField);
                    if (sortOrder.startsWith("ASC")) {
                        cq.orderBy(cb.asc(entityRoot.get(sortField)));
                    }
                    if (sortOrder.startsWith("DESC")) {
                        cq.orderBy(cb.desc(entityRoot.get(sortField)));
                    }
                }
            }
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public int count(Map<String, Object> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(cb.count(entityRoot));
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    private List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> entityRoot, Map<String, Object> filters) {
        javax.persistence.metamodel.Metamodel entityModel = this.getEntityManager().getMetamodel();
        javax.persistence.metamodel.ManagedType<T> entityType = entityModel.managedType(entityClass);
        String fieldTypeName;
        // Add predicates (WHERE clauses) based on filters map
        List<javax.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();
        for (String s : filters.keySet()) {
            javax.persistence.criteria.Path<Object> pkFieldPath;
            if (s.contains(".")) {
                String embeddedIdField = s.split("\\.")[0];
                String embeddedIdMember = s.split("\\.")[1];
                pkFieldPath = entityRoot.get(embeddedIdField).get(embeddedIdMember);
                javax.persistence.metamodel.EmbeddableType<?> embeddableType = entityModel.embeddable(entityType.getAttribute(embeddedIdField).getJavaType());
                fieldTypeName = embeddableType.getAttribute(embeddedIdMember).getJavaType().getName();
            } else {
                pkFieldPath = entityRoot.get(s);
                fieldTypeName = entityType.getAttribute(s).getJavaType().getName();
            }
            if (pkFieldPath != null && fieldTypeName != null) {
                //TODO: a compilação de predicatos não faz a normalização
                //das strings para realizar a busca fonética
                //O dilema:  salvar no banco a versão fonética?
                //Criar uma função em bd e outra na aplicação para converter 
                //o texto armazenado e pesquisado e permitir a comparação?
                if (fieldTypeName.contains("String")) {
                    predicates.add(cb.like(cb.upper((javax.persistence.criteria.Expression) pkFieldPath), filters.get(s).toString().toUpperCase()));
                } else {
                    javax.persistence.criteria.Expression<?> filterExpression = getCastExpression((String) filters.get(s), fieldTypeName, cb);
                    if (filterExpression != null) {
                        predicates.add(cb.equal((javax.persistence.criteria.Expression<?>) pkFieldPath, filterExpression));
                    } else {
                        predicates.add(cb.equal((javax.persistence.criteria.Expression<?>) pkFieldPath, filters.get(s)));
                    }
                }
            }
        }
        return predicates;
    }

    private Expression<?> getCastExpression(String searchValue, String typeName, CriteriaBuilder cb) {
        javax.persistence.criteria.Expression<?> expression = null;
        switch (typeName) {
            case "short":
                expression = cb.literal(Short.parseShort(searchValue));
                break;
            case "byte":
                expression = cb.literal(Byte.parseByte(searchValue));
                break;
            case "int":
                expression = cb.literal(Integer.parseInt(searchValue));
                break;
            case "long":
                expression = cb.literal(Long.parseLong(searchValue));
                break;
            case "float":
                expression = cb.literal(Float.parseFloat(searchValue));
                break;
            case "double":
                expression = cb.literal(Double.parseDouble(searchValue));
                break;
            case "boolean":
                expression = cb.literal(Boolean.parseBoolean(searchValue));
                break;
            default:
                break;
        }
        return expression;
    }

    public LocalDateTime getTimestampOnServer() {
        Query qry = getEntityManager().createNativeQuery("SELECT localtimestamp");
        Timestamp ts = (Timestamp) qry.getSingleResult();
        return ts.toLocalDateTime();
    }

    public LocalDate getDateOnServer() {
        TypedQuery<LocalDate> qry = getEntityManager().createQuery("SELECT localdate", LocalDate.class);
        return qry.getSingleResult();
    }
}
