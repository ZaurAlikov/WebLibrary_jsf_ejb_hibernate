package ru.alikovzaur.library.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;


@FacesValidator("indexFieldsValidator")
public class IndexFieldsValidator implements Validator {

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String id = uiComponent.getId();
        ResourceBundle res = ResourceBundle.getBundle("nls/message", facesContext.getViewRoot().getLocale() );

        try{
            if (id.equals("login")) {
                if (o.toString().length() < 3) {
                    throw new IllegalArgumentException(res.getString("error_login_min_words"));
                }
            }
            if (id.equals("password")) {
                if (o.toString().length() < 5) {
                    throw new IllegalArgumentException(res.getString("error_pass_min_words"));
                }
            }
        } catch (IllegalArgumentException e){
            FacesMessage facesMessage = new FacesMessage(e.getMessage());
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }
    }
}

