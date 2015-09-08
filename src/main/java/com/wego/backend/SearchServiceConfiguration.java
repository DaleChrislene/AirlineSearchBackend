package com.wego.backend;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchServiceConfiguration extends Configuration {
    
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    
    @JsonProperty()
    public DataSourceFactory getDatabase() {
        return database;
    }

    @JsonProperty()
    public void setDatabase(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}