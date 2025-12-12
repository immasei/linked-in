package com.linkedin.post_service.client;

import com.linkedin.post_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionClient {

    @GetMapping("/core/{userId}/first-degree")
    List<PersonDTO> getFirstConnections(@PathVariable Long userId);

}
