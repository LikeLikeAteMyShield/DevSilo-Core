package com.devsilo.integration;

import com.devsilo.domain.ExternalVideo;

import java.util.List;

public interface ExternalServiceClient {

    public List<ExternalVideo> query(String searchPhrase);
}
