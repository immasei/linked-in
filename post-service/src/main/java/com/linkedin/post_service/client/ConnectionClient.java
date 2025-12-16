package com.linkedin.post_service.client;

import com.linkedin.post_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionClient {

    @GetMapping("/core/me/first-degree")
    List<PersonDTO> getFirstConnections();

}
