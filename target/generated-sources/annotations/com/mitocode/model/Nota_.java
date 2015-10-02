package com.mitocode.model;

import com.mitocode.model.Categoria;
import com.mitocode.model.Persona;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-30T20:12:47")
@StaticMetamodel(Nota.class)
public class Nota_ { 

    public static volatile SingularAttribute<Nota, Integer> codigo;
    public static volatile SingularAttribute<Nota, Categoria> categoria;
    public static volatile SingularAttribute<Nota, String> encabezado;
    public static volatile SingularAttribute<Nota, Integer> valorizacion;
    public static volatile SingularAttribute<Nota, Date> fecha;
    public static volatile SingularAttribute<Nota, String> comentarioAdmin;
    public static volatile SingularAttribute<Nota, String> cuerpo;
    public static volatile SingularAttribute<Nota, Persona> persona;

}