package bandesal.gob.sv.entities;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class SuperEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private boolean selected;

	@Transient
	private String status = "NEW";

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
