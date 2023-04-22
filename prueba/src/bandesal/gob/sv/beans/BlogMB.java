package bandesal.gob.sv.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import bandesal.gob.sv.controllers.BandesalController;
import bandesal.gob.sv.entities.Blog;

@Named
@ViewScoped
public class BlogMB extends BandesalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean firstTime;

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
				e.printStackTrace();
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
			throw e;
		}

		return blogs;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		Blog r = (Blog) value;
		return r.getTitle().toLowerCase().contains(filterText);
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
}
