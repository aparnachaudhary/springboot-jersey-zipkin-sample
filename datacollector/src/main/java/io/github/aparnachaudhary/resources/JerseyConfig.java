package io.github.aparnachaudhary.resources;

import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.jaxrs2.TracingFeature;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import io.github.aparnachaudhary.exceptions.GenericExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Feature;

@Component
@Import(TracingClientHttpRequestInterceptor.class)
public class JerseyConfig extends ResourceConfig {

    @Value("${spring.jersey.applicationName}")
    private String applicationName;

    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean Sender sender() {
        return OkHttpSender.create("http://127.0.0.1:9411/api/v1/spans");
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean Reporter<Span> reporter() {
        return AsyncReporter.builder(sender()).build();
    }

    /**
     * Controls aspects of tracing such as the name that shows up in the UI
     */
    @Bean Tracing tracing() {
        return Tracing.newBuilder().localServiceName(applicationName)
                .currentTraceContext(MDCCurrentTraceContext.create()) // puts trace IDs into logs
                .reporter(reporter()).build();
    }

    // decides how to name and tag spans. By default they are named the same as the http method.
    @Bean HttpTracing httpTracing() {
        return HttpTracing.create(tracing());
    }

    public JerseyConfig() {
        register(DataCollectorResource.class);
        register(GenericExceptionMapper.class);
    }

    @PostConstruct
    public void init(){
        Feature tracingFeature = TracingFeature.create(tracing());
        register(tracingFeature);
    }
}
