package bandesal.gob.sv.converters;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import org.apache.log4j.Logger;

import bandesal.gob.sv.entities.Reader;
import bandesal.gob.sv.sessions.BandesalSBSLLocal;
import bandesal.gob.sv.utils.FabricaLogger;

@Named
@RequestScoped
public class ReaderConverter implements Converter {

	@EJB
	protected BandesalSBSLLocal bandesalSBSL;

	private static Logger log = FabricaLogger.getLogger();

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			int id = 0;
			Reader registro;
			if (arg2.trim().equals("")) {
				registro = new Reader();
				return registro;
			} else {
				id = Integer.parseInt(arg2);
			}

			registro = (Reader) bandesalSBSL.findByPk(Reader.class, id);
			if (registro == null) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión",
						"No se pudo convertir el objeto"));
			}
			return registro;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión",
					"No se pudo convertir el objeto"));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			if (arg2 != null && arg2.getClass().equals(Reader.class)) {
				return String.valueOf(((Reader) arg2).getId());
			}
			return null;
		} catch (Exception e) {
			log.error("Error " + e, e);
			return null;
		}
	}

}
