package bandesal.gob.sv.responses;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConsultaLectores implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2822063336385008710L;

	@Id
	private int id;

	private String lector;

	private String blog;

	public ConsultaLectores() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLector() {
		return lector;
	}

	public void setLector(String lector) {
		this.lector = lector;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

}
