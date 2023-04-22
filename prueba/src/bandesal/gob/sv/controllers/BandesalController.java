package bandesal.gob.sv.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import org.primefaces.context.PrimeFacesContext;

import bandesal.gob.sv.entities.SuperEntity;
import bandesal.gob.sv.sessions.BandesalSBSLLocal;

public class BandesalController {

	@EJB
	protected BandesalSBSLLocal bandesalSBSL;

	private boolean error = false;
	private String status = "SEARCH"; // NEW, EDIT, SEARCH

	/* propiedades para objetos de entidad */
	private SuperEntity registro;
	private SuperEntity regSelected;

	/* propiedades para listados de objeto de entidad */
	private List<? extends SuperEntity> listado;
	private List<? extends SuperEntity> filteredListado;
	private List<? extends SuperEntity> selectedListado;

	public void addInfo(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("INFORMACIÓN");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addWarn(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_WARN);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ADVERTENCIA");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addError(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("ERROR");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void addFatal(FacesMessage mensaje) {
		mensaje.setSeverity(FacesMessage.SEVERITY_FATAL);
		if (mensaje.getSummary() == null) {
			mensaje.setSummary("FATAL");
		}
		PrimeFacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public Date getToday() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT-6");
		return Calendar.getInstance(timeZone).getTime();
	}

	/* Acciones al crear nuevo objeto */
	public boolean beforeNew() {
		return true;
	}

	public void onNew() {
		if (beforeNew()) {
			if (getRegistro() != null) {
				try {
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			setStatus("NEW");
			afterNew();
		}
	}

	public void afterNew() {
	}

	/* Acciones al editar objeto */
	public boolean beforeEdit() {
		return true;
	}

	public void onEdit() {
		if (beforeEdit()) {
			setStatus("EDIT");
			afterEdit();
		}
	}

	protected void afterEdit() {
	}

	/* Acciones al visualizar objeto */
	public boolean beforeShow() {
		return true;
	}

	public void onShow() {
		if (beforeShow()) {
			setStatus("SHOW");
			afterShow();
		}
	}

	protected void afterShow() {
	}

	/* Eliminar registro */

	public boolean beforeDelete() {
		return true;
	}

	public void onDelete() {
		try {
			if (beforeDelete()) {
				bandesalSBSL.eliminar(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Se eliminó el registro seleccionado"));
				afterDelete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void afterDelete() {

	}

	/* Acciones al cancelar */
	public boolean beforeCancel() {
		return true;
	}

	public void onCancel() {
		try {
			if (beforeCancel()) {
				if (getRegistro() != null) {
					// setRegistro(null);
					setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					setStatus("SEARCH");
				}
			}
			afterCancel();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void afterCancel() {
	}

	/* Funciones para guardar */
	public boolean beforeSave() {
		return true;
	}

	public void onSave() {
		try {
			if (beforeSave()) {
				if (getStatus().equals("NEW")) {
					onSaveNew();
				}
				if (getStatus().equals("EDIT")) {
					onSaveEdit();
				}
				if (!isError()) {
					afterSave();
				} else if (getRegistro() == null) {
					try {
						setRegistro((SuperEntity) getRegistro().getClass().newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterSave() {
		setStatus("SEARCH");
	}

	/* Funciones para guardar nuevo */
	public boolean beforeSaveNew() {
		return true;
	}

	public void onSaveNew() {
		try {
			if (beforeSaveNew()) {
				bandesalSBSL.insertar(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Registro Guardado con Éxito"));
				afterSaveNew();
			} else {
				setError(true); // para no ejecutar afterSave en el método onSave()
			}
		} catch (Exception e) {
			this.addWarn(new FacesMessage("Error!", "Consulte con el Administrador"));
			setError(true);
			e.printStackTrace();
		}
	}

	public void afterSaveNew() {
	}

	/* Funciones para guardar edicion */
	public boolean beforeSaveEdit() {
		return true;
	}

	public void onSaveEdit() {
		try {
			if (beforeSaveEdit()) {
				bandesalSBSL.actualizar(getRegistro());
				this.addInfo(new FacesMessage("Confirmación", "Registro Guardado con Éxito"));
				afterSaveEdit();
			} else {
				setError(true);
			}
		} catch (Exception e) {
			setError(true);
			this.addWarn(new FacesMessage("Error!", "Consulte con el Administrador"));
			e.printStackTrace();
		}
	}

	public void afterSaveEdit() {
		getRegistro().setSelected(false);
	}

	/* Funciones para seleccion del registro */
	protected boolean beforeRowSelect() {
		return true;
	}

	public void onRowSelect() {
		if (this.beforeRowSelect()) {
			this.setRegistro(this.getRegSelected());
			this.getRegistro().setSelected(true);
			this.afterRowSelect();
		}
	}

	protected void afterRowSelect() {

	}

	public void onShowSelected() {
		this.onRowSelect();
		this.onShow();
	}

	public void onEditSelected() {
		this.onRowSelect();
		this.onEdit();
	}

	public BandesalController() {
		super();
	}

	public BandesalController(SuperEntity registro) {
		this.setRegistro(registro);
	}

	public SuperEntity getRegistro() {
		return registro;
	}

	public void setRegistro(SuperEntity registro) {
		this.registro = registro;
	}

	public SuperEntity getRegSelected() {
		return regSelected;
	}

	public void setRegSelected(SuperEntity regSelected) {
		this.regSelected = regSelected;
	}

	public List<? extends SuperEntity> getListado() {
		return listado;
	}

	public void setListado(List<? extends SuperEntity> listado) {
		this.listado = listado;
	}

	public List<? extends SuperEntity> getFilteredListado() {
		return filteredListado;
	}

	public void setFilteredListado(List<? extends SuperEntity> filteredListado) {
		this.filteredListado = filteredListado;
	}

	public List<? extends SuperEntity> getSelectedListado() {
		return selectedListado;
	}

	public void setSelectedListado(List<? extends SuperEntity> selectedListado) {
		this.selectedListado = selectedListado;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
