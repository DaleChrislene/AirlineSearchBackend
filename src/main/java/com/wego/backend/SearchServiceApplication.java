package com.wego.backend;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.hibernate.SessionFactory;
import org.skife.jdbi.v2.DBI;

import com.wego.backend.airline.health.TemplateHealthCheck;
import com.wego.backend.airline.resource.RouteResource;
import com.wego.backend.entities.Route;
import com.wego.backend.entities.dao.RouteDAO;

public class SearchServiceApplication extends Application<SearchServiceConfiguration> {
	
	private final HibernateBundle<SearchServiceConfiguration> hibernateBundle =
            new HibernateBundle<SearchServiceConfiguration>(Route.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(SearchServiceConfiguration configuration) {
                    return configuration.getDatabase();
                }
            };
	
	public static void main(String[] args) throws Exception {
		new SearchServiceApplication().run(args);
	}

	@Override
	public String getName() {
		return "airline-search";
	}

	@Override
	public void initialize(Bootstrap<SearchServiceConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<SearchServiceConfiguration>() {
	        @Override
	            public DataSourceFactory getDataSourceFactory(SearchServiceConfiguration configuration) {
	                return configuration.getDatabase();
	            }
	    });
		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(SearchServiceConfiguration configuration,
			Environment environment) throws Exception {
	    
	    final RouteDAO routeDao = new RouteDAO(hibernateBundle.getSessionFactory());

		environment.jersey().register(new RouteResource(routeDao));
	}

}