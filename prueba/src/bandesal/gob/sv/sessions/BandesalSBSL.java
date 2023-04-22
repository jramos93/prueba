package bandesal.gob.sv.sessions;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import bandesal.gob.sv.entities.SuperEntity;

@Stateless
@LocalBean
public class BandesalSBSL implements BandesalSBSLLocal {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private EntityManager em;

	private boolean transActiva = false;

	public BandesalSBSL() {
	}

	public SuperEntity insertar(SuperEntity entidad) throws Exception {

		EntityTransaction et = null;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			this.getEm().persist(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!this.transActiva && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return entidad;
	}
	

	@Override
	public SuperEntity actualizar(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			entidad = (SuperEntity) this.getEm().merge(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!isTransActiva() && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return entidad;
	}

	@Override
	public void eliminar(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		int pk = 0;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			PersistenceUnitUtil puu = this.getEm().getEntityManagerFactory().getPersistenceUnitUtil();
			pk = (int) puu.getIdentifier(entidad);
			System.out.println("pk "+pk);
			entidad = (SuperEntity) this.getEm().find(entidad.getClass(), pk);
			this.getEm().remove(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!this.transActiva && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}

	}

	@Override
	public List<?> findByNamedQuery(Class<? extends Serializable> clase, String nameQuery, Object[] arrayParametros)
			throws Exception {
		List<?> lista = null;
		try {
			TypedQuery<? extends Serializable> typedQuery = this.getEm().createNamedQuery(nameQuery, clase);
			if (arrayParametros != null)
				for (int i = 0; i < arrayParametros.length; i++)
					typedQuery.setParameter(i + 1, arrayParametros[i]);
			typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
			lista = typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return lista;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		if (this.em == null || !this.em.isOpen()) {
			System.out.println("create");
			this.em = this.emf.createEntityManager();
		}
		return this.em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public boolean isTransActiva() {
		return transActiva;
	}

	public void setTransActiva(boolean transActiva) {
		this.transActiva = transActiva;
	}
}
