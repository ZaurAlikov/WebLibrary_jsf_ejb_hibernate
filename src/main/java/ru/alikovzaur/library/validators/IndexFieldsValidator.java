package ru.alikovzaur.library.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("indexFieldsValidator")
public class IndexFieldsValidator implements Validator {

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String id = uiComponent.getId();
        if (id.equals("login")) {
            if (o.toString().length() < 3) {
                FacesMessage facesMessage = new FacesMessage("Логин должен быть не короче 3 символов");
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(facesMessage);
            }
        } else if (id.equals("password")) {
            if (o.toString().length() < 5) {
                FacesMessage facesMessage = new FacesMessage("Пароль должен быть не короче 5 символов");
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(facesMessage);
            }
        }
    }
}

