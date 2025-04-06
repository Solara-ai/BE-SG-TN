package org.se06203.besgtn.service.client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class WebclientURIBuilder {
    public abstract String getBaseUrl();
}
