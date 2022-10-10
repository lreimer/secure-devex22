package de.qaware.cloud.devex22.nvd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class NvdConnector {
    
    @Value("${nvd.api.uri}")
    private String nvdApiUri;

    public String queryCve(String cveId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(nvdApiUri));
        return restTemplate.getForObject(nvdApiUri + "?cveId=" + cveId, String.class);
    }    

}
