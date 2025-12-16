package com.linkedin.connection_service.controller;

import com.linkedin.connection_service.auth.UserContextHolder;
import com.linkedin.connection_service.dto.PersonDTO;
import com.linkedin.connection_service.entity.Person;
import com.linkedin.connection_service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.linkedin.connection_service.service.ConnectionService;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    private final PersonMapper personMapper;

    // my direct connections
    @GetMapping("/me/first-degree")
    public ResponseEntity<List<PersonDTO>> getFirstConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        List<Person> connections = connectionService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(personMapper.toDTOs(connections));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable("userId") Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        return ResponseEntity.ok(connectionService.sendConnectionRequest(senderId, receiverId));
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable("userId") Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(senderId, receiverId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable("userId") Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(senderId, receiverId));
    }

//    @GetMapping("/users/{userId}/mutual")
//    @GetMapping("/me/people-you-may-know")
//    public ResponseEntity<List<Person>> getFirstConnections(@RequestHeader("X-User-Id") Long userId) {
//        return ResponseEntity.ok(connectionService.getSecondDegreeConnections(userId));
//    }

    // follow-only || connection-only || both

}
