/*
package com.reddit.notification.service;

import message.UserNotification;
import com.reddit.notification.repository.NotificationRepository;
import com.reddit.notification.repository.UserRepository;
import message.PostDto;
import message.PostedBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserRepository userRepository;

    private UserNotification userNotification;

    private UserNotification follower;

    private PostDto postDto;

    private PostedBy postedBy;


    @BeforeEach
    public void beforeEach() {
        this.userNotification = new UserNotification(1L, "halco", "halco1002@email.com","image", new HashSet<>(), new HashSet<>());
        this.follower = new UserNotification(1L, "halco", "halco1002@email.com", "image", new HashSet<>(), new HashSet<>());

        this.userNotification.getFollowers().add(follower);
        this.follower.getFollowing().add(userNotification);

        this.postedBy = new PostedBy(userNotification.getId(), userNotification.getEmail(), userNotification.getUsername(), userNotification.getImageUrl());

        this.postDto = new PostDto(10L, "title", "desc", "postImage", true, postedBy);


        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(Optional.of(userNotification));
    }






}
*/
