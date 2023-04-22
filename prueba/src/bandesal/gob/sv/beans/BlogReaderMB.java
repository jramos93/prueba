package bandesal.gob.sv.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import bandesal.gob.sv.controllers.BandesalController;
import bandesal.gob.sv.responses.ConsultaLectores;

@Named
@ViewScoped
public class BlogReaderMB extends BandesalController implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean firstTime;

	private List<ConsultaLectores> lectores;
	private List<ConsultaLectores> lectoreFiltrado;

	public BlogReaderMB() {
		super();
	}

	@PostConstruct
	public void init() {
		firstTime = true;
	}

	@SuppressWarnings("unchecked")
	public void buscarTodos() {

		try {
			lectores = (List<ConsultaLectores>) bandesalSBSL.findByNamedQuery(ConsultaLectores.class, "readers.by.blog",
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error "+e, e);
		}

	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		ConsultaLectores c = (ConsultaLectores) value;
		return c.getBlog().toLowerCase().contains(filterText) || c.getLector().toLowerCase().contains(filterText);
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public List<ConsultaLectores> getLectores() {
		return lectores;
	}

	public void setLectores(List<ConsultaLectores> lectores) {
		this.lectores = lectores;
	}

	public List<ConsultaLectores> getLectoreFiltrado() {
		return lectoreFiltrado;
	}

	public void setLectoreFiltrado(List<ConsultaLectores> lectoreFiltrado) {
		this.lectoreFiltrado = lectoreFiltrado;
	}

}
