package bandesal.gob.sv.sessions;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import bandesal.gob.sv.entities.SuperEntity;

@Local
public interface BandesalSBSLLocal {

	
	SuperEntity insertar(SuperEntity entidad) throws Exception;

	List<?> findByNamedQuery(Class<? extends Serializable> clase, String nameQuery, Object[] arrayParametros)
			throws Exception;

	SuperEntity actualizar(SuperEntity entidad) throws Exception;

	void eliminar(SuperEntity entidad) throws Exception;

	Object findByPk(Class<? extends Serializable> clase, Object parametros);
}
