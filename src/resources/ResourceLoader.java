package resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("pubhub")
public class ResourceLoader extends Application {

	private Set<Class<?>> instanceResources = new HashSet<>();
	private Set<Object> singletonResources = new HashSet<>();

	public ResourceLoader() {
		System.out.println("Ressources Loaded");
		instanceResources.add(Resource.class);

	}

	@Override
	public Set<Class<?>> getClasses() {
		return this.instanceResources;
	}

	@Override
	public Set<Object> getSingletons() {
		return this.singletonResources;
	}

}