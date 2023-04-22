package bandesal.gob.sv.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the blogs database table.
 * 
 */
@Entity
@Table(name="blogs")
@NamedQuery(name="Blog.findAll", query="SELECT b FROM Blog b")
public class Blog extends SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	private String title;

	//bi-directional many-to-many association to Reader
	@ManyToMany
	@JoinTable(
		name="blogs_readers"
		, joinColumns={
			@JoinColumn(name="b_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="r_id")
			}
		)
	private List<Reader> readers;

	public Blog() {
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

}