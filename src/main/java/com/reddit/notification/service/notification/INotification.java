package com.reddit.notification.service.notification;

import com.reddit.notification.model.Notification;
import message.FollowUnfollowNotification;
import message.PostDto;
import message.PostedBy;
import message.UserProfile;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

public interface INotification {

    void followNotification(FollowUnfollowNotification followUnfollowNotification);

    void commentNotification();

    void newPostNotification(PostDto postDto, Acknowledgment acknowledgment);


    List<Notification> getAllNotificationsByUserId(Long userId);

    Notification saveNotification(Notification request);

}
