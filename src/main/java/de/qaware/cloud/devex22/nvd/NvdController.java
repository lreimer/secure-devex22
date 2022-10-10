package de.qaware.cloud.devex22.nvd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NvdController {

    private final NvdConnector connector;

    @Autowired
    public NvdController(NvdConnector connector) {
        this.connector = connector;
    }

    @GetMapping(value = "/cves/{cveId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getWeather(@PathVariable(value = "cveId") String cveId) {        
        return ResponseEntity.ok(connector.queryCve(cveId));
    }
}
