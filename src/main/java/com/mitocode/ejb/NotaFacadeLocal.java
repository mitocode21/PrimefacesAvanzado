package com.mitocode.ejb;

import com.mitocode.model.Nota;
import java.util.List;
import javax.ejb.Local;

@Local
public interface NotaFacadeLocal {

    void create(Nota nota);

    void edit(Nota nota);

    void remove(Nota nota);

    Nota find(Object id);

    List<Nota> findAll();

    List<Nota> findRange(int[] range);

    int count();
}
