package com.linkedin.notification_service.client;

import com.linkedin.notification_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionClient {

    @GetMapping("/core/me/first-degree")
    List<PersonDTO> getFirstConnections(@RequestHeader("X-User-Id") Long userId);

}
