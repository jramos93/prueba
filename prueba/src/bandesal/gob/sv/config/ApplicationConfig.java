package bandesal.gob.sv.config;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import bandesal.gob.sv.api.APIRest;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();

		AddRestResourceClasses(resources);
		return resources;
	}

	private void AddRestResourceClasses(Set<Class<?>> resources) {
		resources.add(APIRest.class);
	}

}
