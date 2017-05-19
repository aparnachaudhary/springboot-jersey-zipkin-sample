package io.github.aparnachaudhary.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("")
@Component
public class ExampleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResource.class);


    private RestTemplate restTemplate;

    public ExampleResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Path("collectData")
    @GET
    public String collectDataFromMachine(@Context UriInfo uriInfo) {
        LOGGER.info("in {}", uriInfo.getAbsolutePath()); // arbitrary log message to show integration works
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // make a relative request to the same process
        return restTemplate.getForObject("http://localhost:8082/machineconnector/healthReport", String.class);
    }

}
