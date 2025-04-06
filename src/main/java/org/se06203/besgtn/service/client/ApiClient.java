package org.se06203.besgtn.service.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.dto.request.ApiChatReq;
import org.se06203.besgtn.dto.response.ApiChatRes;
import org.se06203.besgtn.utils.RequestContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {

    private final URIBuilder uriBuilder;
    @Resource(name = "baseContext")
    private final RequestContext requestContext;

    protected WebclientURIBuilder getUriBuilder() {
        return uriBuilder;
    }

public BaseDataResponse<ApiChatRes> getChatResponse(ApiChatReq chatReq) {
    var uri = uriBuilder.getChatResponse();

    try {
        return getWebClient()
                .post()
                .uri(uri)
                .bodyValue(chatReq)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseDataResponse<ApiChatRes>>() {})
                .block();
    } catch (Exception e) {
        log.error("getChatResponse - Error: ", e);
        throw new BaseRuntimeException(ErrorCodeMsg.CAN_NOT_GET_CHAT_RESPONSE);
    }
}

    private WebClient getWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        uriBuilder.getConnectTimeout())
                .doOnConnected(connection ->
                        connection.addHandlerLast(
                                        new ReadTimeoutHandler(uriBuilder.getRequestTimeout(),
                                                TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(
                                        uriBuilder.getRequestTimeout(),
                                        TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(getUriBuilder().getBaseUrl())
                .defaultHeaders(httpHeaders ->
                        httpHeaders.addAll(requestContext.getCurrenRequestContextHeaders()))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
