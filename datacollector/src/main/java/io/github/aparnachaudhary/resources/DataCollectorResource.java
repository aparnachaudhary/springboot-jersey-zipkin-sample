package io.github.aparnachaudhary.resources;

import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("")
@Component
public class DataCollectorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorResource.class);

    @Autowired
    private TracingClientHttpRequestInterceptor clientInterceptor;


    private RestTemplate restTemplate;

    public DataCollectorResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void init(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(clientInterceptor);
        restTemplate.setInterceptors(interceptors);
    }

    @Path("collectData")
    @GET
    public String collectDataFromMachine(@Context UriInfo uriInfo) {
        LOGGER.info("in {}", uriInfo.getAbsolutePath()); // arbitrary log message to show integration works
        // make a relative request to the same process
        return restTemplate.getForObject("http://localhost:8082/machineconnector/healthReport", String.class);
    }

}
