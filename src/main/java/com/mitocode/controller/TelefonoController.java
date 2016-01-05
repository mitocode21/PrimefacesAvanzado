package com.mitocode.controller;

import com.mitocode.ejb.TelefonoFacadeLocal;
import com.mitocode.model.Telefono;
import com.mitocode.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class TelefonoController implements Serializable {

    @EJB
    private TelefonoFacadeLocal telefonoEJB;
    @Inject
    private Telefono telefono;
    private List<Telefono> telefonos;
    private String accion;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {        
        this.accion = accion;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    @PostConstruct
    public void init() {
        telefonos = telefonoEJB.findAll();
    }

    public void registrar() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        telefono.setPersona(us.getCodigo());
        telefonoEJB.create(telefono);
        telefonos = telefonoEJB.findAll();        
    }

    public void leer(Telefono telfSeleccion) {
        telefono = telfSeleccion;
        this.setAccion("M");
    }
    
    public void modificar(Telefono tel){
        telefonoEJB.edit(tel);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se modificó"));
    }
    
    public void mostrarDialogo(){
        //logica
        this.setAccion("R");
        
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('wdialogo').show();");
    }
}
