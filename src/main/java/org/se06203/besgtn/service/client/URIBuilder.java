package org.se06203.besgtn.service.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "webclient")
public class URIBuilder extends WebclientURIBuilder {

    private int requestTimeout;
    private int connectTimeout;

    private String baseUrl;
    private String postChatPath;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    public Function<UriBuilder, URI> getChatResponse() {
        return uriBuilder -> uriBuilder
                .path(postChatPath)
                .build();
    }
}
