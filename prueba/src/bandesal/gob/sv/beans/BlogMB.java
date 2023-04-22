package bandesal.gob.sv.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bandesal.gob.sv.controllers.BandesalController;
import bandesal.gob.sv.entities.Blog;
import bandesal.gob.sv.entities.Reader;

@Named
@ViewScoped
public class BlogMB extends BandesalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean firstTime;

	private Reader reader;
	private List<Reader> readers;

	@Inject
	ReaderMB readerMB;

	public BlogMB() {
		super(new Blog());
	}

	@PostConstruct
	public void init() {
		firstTime = true;
	}

	@SuppressWarnings("unchecked")
	public void incializar() {
		// Se ejecuta solo la primera ves
		if (firstTime) {

			try {
				setListado((List<Blog>) buscarBlogs());
			} catch (Exception e) {
				log.error("Error " + e, e);
			}
			firstTime = Boolean.FALSE;
		}
	}

	@SuppressWarnings("unchecked")
	public List<?> buscarBlogs() throws Exception {
		List<Blog> blogs = null;
		try {
			blogs = (List<Blog>) bandesalSBSL.findByNamedQuery(Blog.class, "all.blogs", null);

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error " + e, e);
			throw e;
		}

		return blogs;
	}

	public void buscarLectores() {
		try {
			readers = (List<Reader>) readerMB.buscarLectores();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error " + e, e);
		}
	}

	public void guardarLectores() {
		if (getRegistro().getReaders() != null) {

			onSaveEdit();
		}
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Blog r = (Blog) value;
		return r.getTitle().toLowerCase().contains(filterText);
	}

	public void agregarLector() {
		if (reader != null) {
			getRegistro().addReader(reader);
			onSaveEdit();
		}
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public Blog getRegistro() {
		// TODO Auto-generated method stub
		return (Blog) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> getListado() {
		// TODO Auto-generated method stub
		return (List<Blog>) super.getListado();
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

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public List<Reader> getReaders() {
		return readers;
	}

	public void setReaders(List<Reader> readers) {
		this.readers = readers;
	}

}
