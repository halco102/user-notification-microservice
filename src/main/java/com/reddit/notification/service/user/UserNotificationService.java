package com.reddit.notification.service.user;

import com.reddit.notification.exception.NotFoundException;
import com.reddit.notification.model.Notification;
import com.reddit.notification.model.NotificationType;
import com.reddit.notification.repository.NotificationRepository;
import com.reddit.notification.repository.UserRepository;
import com.reddit.notification.service.notification.INotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.FollowUnfollowNotification;
import message.PostDto;
import message.UserProfile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserNotificationService{

    private final UserRepository userRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String newUserMessage = "Welcome to my app :)";

    private final INotification iNotification;

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = {"CREATE_USER_EVENT"}, containerFactory = "kafkaListenerContainerFactory")
    private void addNewlyCreatedUser(@Payload UserProfile userPro, Acknowledgment acknowledgment) {

        var fetchUser = userRepository.findById(String.valueOf(userPro.getId()));

        if (fetchUser.isEmpty()) {
            var savedUser = userRepository.save(userPro);

            var saveNotification = iNotification.saveNotification(new Notification(null, savedUser, NotificationType.NEW_USER, newUserMessage));

            simpMessagingTemplate.convertAndSendToUser(savedUser.getUsername(), "/queue/notification", saveNotification);
        }
        else
            log.info("User exists");


        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = "NEW_POST", containerFactory = "kafkaListenerContainerFactory")
    public void newPostNotification(@Payload PostDto postDto, Acknowledgment acknowledgment) {

        String usersId = String.valueOf(postDto.getPostedBy().getId());
        AtomicReference<Notification> saveNotification = new AtomicReference<>(new Notification());

        //find user that triggered the action
        var findUser = userRepository.findById(usersId);

        if (findUser.isEmpty()) {
            throw new NotFoundException("The user does not exist in this db");
        }

        //for each follower send message and save to notification db
        findUser.get().getFollowers().forEach(follower -> {
            var user = userRepository.findById(String.valueOf(follower.getId())).get();
            Notification notification = new Notification(null, user, NotificationType.NEW_POST, postDto);
            saveNotification.set(notificationRepository.save(notification));

            //send notification object to frontend
            simpMessagingTemplate.convertAndSendToUser(follower.getUsername(), "/queue/notification", saveNotification.get());


            log.info("Notificaion " + saveNotification.get());
        });

        acknowledgment.acknowledge();
    }



    @KafkaListener(topics = "USER_FOLLOW_EVENT", containerFactory = "kafkaListenerContainerFactory")
    private void sendNotificationWhenUserFollows(@Payload FollowUnfollowNotification followUnfollowNotification) {
        var findUser = userRepository.findById(String.valueOf(followUnfollowNotification.getMainUserId()));

        if (findUser.isEmpty()) {
            log.info("User does not exist");
            return;
        }

        var notification = iNotification.saveNotification(new Notification(null, findUser.get(), NotificationType.FOLLOW, followUnfollowNotification.getFollower()));
        simpMessagingTemplate.convertAndSendToUser(findUser.get().getUsername(), "/queue/notification", notification.getNotification());
    }


    @KafkaListener(topics = {"USER_UNFOLLOW_EVENT"}, containerFactory = "kafkaListenerContainerFactory")
    private void updateUserWhenHeGainsOrLossesFollowers(@Payload UserProfile userProfile) {
        var findUser = userRepository.findById(String.valueOf(userProfile.getId()));

        if (!findUser.isEmpty()) {
            var update = userRepository.save(userProfile);
            //simpMessagingTemplate.convertAndSend("/user", update);
        }
    }

}
