package com.reddit.notification.controller;

import com.reddit.notification.service.notification.INotification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final INotification iNotification;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllUsersNotification(@PathVariable Long userId) {
        return new ResponseEntity<>(iNotification.getAllNotificationsByUserId(userId), HttpStatus.OK);
    }
}
