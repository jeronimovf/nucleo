package br.jus.trt23.nucleo.controllers;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.nucleo.enums.EActiveAction;
import br.jus.trt23.nucleo.handlers.GenericLazyDataModel;
import br.jus.trt23.nucleo.handlers.Jsf;
import br.jus.trt23.nucleo.qualifiers.MessageBundle;
import br.jus.trt23.nucleo.sessions.AbstractFacade;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class AbstractController<T extends EntidadeGenerica> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ValidatorFactory validatorFactory;
    @Inject
    private Validator validator;

    @Inject
    private AbstractFacade<T> facade;
    private Class<T> itemClass;
    //o escopo original de selected no CRUD PF é private
    protected T selected;
    protected Collection<T> items;
    protected GenericLazyDataModel<T> lazyItems;

    //essas listas são utilizadas nas DataTable
    private List<T> selectedItems;
    private List<T> filteredItems;

    private Map<String, Object> permanentFilters;

    //o messagePrefix é utilizado para a obtenção de strings de bundle
    protected abstract String getMessagePrefix();

    @Inject
    @MessageBundle
    //o messages é utilizado para obtenção de propriedades do bundle
    //que são gerais, não dependendo de um controle específico e do
    //seu messagePrefix
    protected transient ResourceBundle messages;

    public AbstractController() {
    }

    public AbstractController(Class<T> itemClass) {
        this();
        this.itemClass = itemClass;
    }

    public T getEntity(java.lang.Long id) {
        return (T) getFacade().find(id);
    }

    public Collection<T> getItems() {
        if (items == null) {
            items = this.facade.findAll();
        }
        return items;
    }

    public GenericLazyDataModel<T> getLazyItems() {
        if (lazyItems == null) {
            if (null == getPermanentFilters()) {
                lazyItems = new GenericLazyDataModel<>(getFacade());
            } else {
                lazyItems = new GenericLazyDataModel<>(getFacade(), getPermanentFilters());
            }
        }

        return lazyItems;
    }

    public void setLazyItems(GenericLazyDataModel<T> lazyItems) {
        this.lazyItems = lazyItems;
    }

    public void setLazyItems(Collection<T> items) {
        if (items instanceof List) {
            lazyItems = new GenericLazyDataModel<>((List<T>) items);
        } else {
            lazyItems = new GenericLazyDataModel<>(new ArrayList<>(items));
        }
    }

    //este método é chamado antes da execução do saveOrCreate permitindo
    //que as classes que herdam do controlador abstrato o redefinam
    //e possam, por exemplo, preencher atributos que serão salvos em cascata
    public String beforeCreate() {
        return "Create";
    }

    public String create() {
        String msg;
        try {
            getFacade().create(selected);
            msg = getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "List";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String create(T obj) {
        String msg;
        try {
            getFacade().create(obj);
            msg = getMsgCreated();
            Jsf.addSuccessMessage(msg);
            return "List";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String saveOrCreate() throws Exception {
            validate(selected);
            if (getSelected().getId() != null){ 
                beforeUpdate();
                return update();
            } else {
                beforeCreate();
                return create();
            }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String saveOrCreate(T obj) throws Exception {
            validate(obj);

            if (obj.getId() != null) {
                beforeUpdate();
                return update(obj);
            } else {
                beforeCreate();
                return create(obj);
            }
    }

    public String prepareEdit() {
        setSelected((T) getLazyItems().getRowData());
        return "Edit";
    }

    public void refresh() {
        getFacade().refresh(selected);
    }

    public void refresh(T entidade) {
        getFacade().refresh(entidade);
    }

    public String update() throws Exception {
        String msg;
        try {
            getFacade().edit(selected);
            msg = getMsgUpdated();
            Jsf.addSuccessMessage(msg);
            return "Edit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String update(T obj) {
        String msg;
        try {
            getFacade().edit(obj);
            msg = getMsgUpdated();
            Jsf.addSuccessMessage(msg);
            return "Edit";
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
            return null;
        }
    }

    public String destroy() {
        String msg;
        for (T obj : getSelectedItems()) {
            performDestroy(obj);
        }
        msg = MessageFormat.format(messages.getString(getMessagePrefix().concat("_Deleted")), getSelectedItems().size());
        Jsf.addSuccessMessage(msg);
        refreshList();
        return "List";
    }

    private void performDestroy(T item) {
        String msg;
        try {
            getFacade().remove(item);
        } catch (Exception e) {
            msg = messages.getString("PersistenceErrorOccured");
            Jsf.addErrorMessage(e, msg);
        }
    }

    public List<T> complete(String criteria) {
        return getFacade().complete(criteria);
    }
    //TODO: o método getString do resource messages gera uma exceção 
    //MissingResourceException no caso da chave não ser encontrada
    //Essa exceção pode ser tratada e fazer o label exibido ser igual
    //a chave, facilitando a depuração.
    public String getMsgPageTitle() {
        return messages.getString(getMessagePrefix().concat("_Title_").
                concat(EActiveAction.VIEW.toString()));
    }

    public String getMsgEmptyList() {
        return messages.getString(getMessagePrefix().concat("_Empty"));
    }

    public String getMsgCreated() {
        return messages.getString(getMessagePrefix().concat("_Created"));
    }
    
    private String getMsgUpdated() {
        return messages.getString(getMessagePrefix().concat("_Updated"));
    }    

    public String getMsgNotFound() {
        return messages.getString(getMessagePrefix().concat("_NotFound"));
    }

    public String getMsgFieldLabel(String field) {
        return messages.getString(getMessagePrefix().concat("_Field_").concat(field));
    }

    public String getMsgFieldHint(String field) {
        return messages.getString(getMessagePrefix().concat("_Field_Hint_").concat(field));
    }

    public String getMsgAction(String action) {
        return messages.getString(getMessagePrefix().concat("_Action_").concat(action));
    }

    public String getDlgCreateHeader(String header) {
        String header_ = messages.getString(getMessagePrefix().concat("_TabHeader_").concat(header));
        return header_.concat(" (").concat(messages.getString("CreateLink")).concat(")");
    }

    public String getDlgEditHeader(String header) {
        String header_ = messages.getString(getMessagePrefix().concat("_TabHeader_").concat(header));
        return header_.concat(" (").concat(messages.getString("EditLink")).concat(")");
    }

    public String getResponseCreated(String child) {
        return messages.getString(getMessagePrefix().concat("_Response_").concat(child).concat("_Created"));
    }

    public String getTabHeader(String header) {
        return messages.getString(getMessagePrefix().concat("_TabHeader_").concat(header));
    }

    public List<SelectItem> getItemsAvailableSelectMany() {
        return Jsf.getSelectItems(getFacade().findAll(), false);
    }

    public List<SelectItem> getItemsAvailableSelectOne() {
        return Jsf.getSelectItems(getFacade().findAll(), true);
    }

    private void refreshList() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formRoot:listItems");
    }

    /**
     * Inform the user interface whether any validation error exist on a page.
     *
     * @return a logical value whether form validation has passed or failed
     */
    public boolean isValidationFailed() {
        return Jsf.isValidationFailed();
    }

    /**
     * Retrieve all messages as a String to be displayed on the page.
     *
     * @param clientComponent the component for which the message applies
     * @param defaultMessage a default message to be shown
     * @return a concatenation of all messages
     */
    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return Jsf.getComponentMessages(clientComponent, defaultMessage);
    }

    /**
     * Retrieve a collection of Entity items for a specific Controller from
     * another JSF page via Request parameters.
     */
    @PostConstruct
    public void initParams() {
        Object paramItems = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(itemClass.getSimpleName() + "_items");
        if (paramItems != null) {
            setItems((Collection<T>) paramItems);
            setLazyItems((Collection<T>) paramItems);
        }
    }

    //Essa função é necessária porque para os composite componets
    //não é aceito passar uma String diretamente para um atributo que
    //será associado a um action internamente. 
    public String goBackTo(String str) {
        return str;
    }

    public void validate(T entity) {
        Set<ConstraintViolation<T>> constraintViolations
                = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Jsf.addErrorMessagesFromConstraintViolations(constraintViolations);
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    private String beforeUpdate() {
        return "Update";
    }
    
    //o método prepare create é executado antes que se abra o formulário de 
    //inclusão, chamado na action do botão novo.  sobrescrevê-lo permite
    //inicializar propriedades que serão consumidas durante a inclusão
    public String prepareCreate() {
        setSelected((T) getFacade().newInstance());
        return "Create";
    }    

}
