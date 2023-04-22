package bandesal.gob.sv.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the blogs database table.
 * 
 */
@Entity
@Table(name = "blogs")
public class Blog extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private String title;

	// bi-directional many-to-many association to Reader
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "blogs_readers", joinColumns = { @JoinColumn(name = "b_id") }, inverseJoinColumns = {
			@JoinColumn(name = "r_id") })
	private List<Reader> readers;

	public Blog() {
	}

	public Blog(int id, String description, String title, List<Reader> readers) {
		super();
		this.id = id;
		this.description = description;
		this.title = title;
		this.readers = readers;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Reader> getReaders() {
		return this.readers;
	}

	public void setReaders(List<Reader> readers) {
		this.readers = readers;
	}

	public void addReader(Reader r) {
		this.readers.add(r);
		r.getBlogs().add(this);

	}

	public void removeReader(int id) {
		Reader r = this.readers.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
		if (r != null) {
			this.readers.remove(r);
			r.getBlogs().remove(this);
		}
	}
}