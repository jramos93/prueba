package bandesal.gob.sv.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import bandesal.gob.sv.controllers.BandesalController;
import bandesal.gob.sv.entities.Reader;

@Named
@ViewScoped
public class ReaderMB extends BandesalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean firstTime;

	public ReaderMB() {
		super(new Reader());
	}

	@PostConstruct
	public void init() {
		firstTime = true;

	}

	@SuppressWarnings("unchecked")
	public void incializar() {
		System.out.println("init");
		// Se ejecuta solo la primera ves
		if (firstTime) {

			try {
				setListado((List<Reader>) buscarLectores());
			} catch (Exception e) {
				e.printStackTrace();
			}
			firstTime = Boolean.FALSE;
		}
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarLectores() throws Exception {
		List<Reader> lectores = null;
		System.out.println("buscar");
		try {
			lectores = (List<Reader>) bandesalSBSL.findByNamedQuery(Reader.class, "all.readers", null);

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return lectores;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Reader r = (Reader) value;
		return r.getName().toLowerCase().contains(filterText);
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public Reader getRegistro() {
		// TODO Auto-generated method stub
		return (Reader) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reader> getListado() {
		// TODO Auto-generated method stub
		return (List<Reader>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}
	
	@Override
	public void afterDelete() {
		// TODO Auto-generated method stub
		getListado().remove(getRegistro());
		super.afterDelete();
	}
}
