package com.mitocode.controller;

import com.mitocode.ejb.MenuFacadeLocal;
import com.mitocode.model.Menu;
import com.mitocode.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named
@SessionScoped
public class MenuController implements Serializable {

    @EJB
    private MenuFacadeLocal EJBMenu;
    private List<Menu> lista;
    private MenuModel model;

    @PostConstruct
    public void init() {
        this.listarMenus();
        model = new DefaultMenuModel();
        //this.establecerPermisos();
        this.recorreMenu();
    }

    public void listarMenus() {
        try {
            lista = EJBMenu.findAll();
        } catch (Exception e) {
            //mensaje jsf
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    private DefaultSubMenu recursivoSubMenu(DefaultSubMenu firstSubmenu, int cveModulo) {
        List<Menu> lstHijos = ListaItem(cveModulo);
        for (Menu m : lstHijos) {
            if (m.getTipo().equals("S")) {
                DefaultSubMenu subMenu = new DefaultSubMenu(m.getNombre());
                firstSubmenu.addElement(recursivoSubMenu(subMenu, m.getCodigo()));
            } else {
                DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                item.setUrl(m.getUrl());                
                firstSubmenu.addElement(item);     
            }
        }
        return firstSubmenu;
    }
     private List<Menu> ListaItem(int codigoSubmenu) {
        List<Menu> lstItem = new ArrayList<>();
        for (Menu m : lista) {
            if ((m.getSubmenu()==null?0:m.getSubmenu().getCodigo()) == codigoSubmenu) {
                lstItem.add(m);
            }
        }
        return lstItem;
    }
     public void recorreMenu() { 
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        model = new DefaultMenuModel();
        List<Menu> lstItem = ListaItem(0);     
       for (Menu m : lstItem) {
           if (m.getTipo().equals("S") && m.getSubmenu()==null && m.getTipoUsuario().equals(us.getTipo())) {
               DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.getNombre());
               model.addElement(recursivoSubMenu(firstSubmenu, m.getCodigo()));
           } else {
               DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
               item.setUrl(m.getUrl());              
               model.addElement(item);
           }
       }
    }
    public void establecerPermisos() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        for (Menu m : lista) {
            if (m.getTipo().equals("S") && m.getTipoUsuario().equals(us.getTipo())) {
                DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.getNombre());
                for (Menu i : lista) {
                    Menu submenu = i.getSubmenu();
                    if (submenu != null) {
                        if (submenu.getCodigo() == m.getCodigo()) {
                            DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
                            item.setUrl(i.getUrl());
                            firstSubmenu.addElement(item);
                        }
                    }
                }
                model.addElement(firstSubmenu);
            } else {
                if (m.getSubmenu() == null && m.getTipoUsuario().equals(us.getTipo())) {
                    DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                    item.setUrl(m.getUrl());
                    model.addElement(item);
                }
            }
        }
    }
    
    public void cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
    public String mostrarUsuarioLogeado(){
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return us.getUsuario();
    }
}
