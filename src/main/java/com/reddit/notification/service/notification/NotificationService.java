package com.reddit.notification.service.notification;

import com.reddit.notification.model.Notification;
import com.reddit.notification.model.NotificationType;
import com.reddit.notification.repository.NotificationRepository;
import com.reddit.notification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.FollowUnfollowNotification;
import message.PostDto;
import message.UserProfile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotification{

    private final NotificationRepository notificationRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserRepository userRepository;


    /*
    * Notification when someone follows user
    * */
    @Override
    //@KafkaListener(topics = {"USER_FOLLOW_EVENT", "USER_UNFOLLOW_EVENT"}, containerFactory = "kafkaListenerContainerFactory")
    public void followNotification(@Payload FollowUnfollowNotification followUnfollowNotification) {
        Notification notification = new Notification();

        notification.setNotificationType(NotificationType.FOLLOW);

    }


    /*
    * Notification action after a reply on comment
    * */
    @Override
    public void commentNotification() {

    }

    /*
    * Notification when a user that you follow posts
    * */
    @KafkaListener(topics = "NEW_POST", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void newPostNotification(@Payload PostDto postDto, Acknowledgment acknowledgment) {

        //find user that triggered the action
        var findUser = userRepository.findById(String.valueOf(postDto.getPostedBy().getId()));

        //for each follower send message and save to notification db
        findUser.get().getFollowers().forEach(follower -> {
            var user = userRepository.findById(String.valueOf(follower.getId())).get();
            Notification notification = new Notification(null, user, NotificationType.NEW_POST, postDto);
            simpMessagingTemplate.convertAndSendToUser(follower.getUsername(), "/queue/post", postDto);

            var saveNotification = notificationRepository.save(notification);

            log.info("Notificaion " + saveNotification);
        });

        acknowledgment.acknowledge();
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        var temp = notificationRepository.getAllNotificationsByUserId(userId);
        return temp;
    }

    @Override
    public Notification saveNotification(Notification request) {
        return notificationRepository.save(request);
    }
}
