package com.reddit.notification.service.user;

import message.UserNotification;

public interface IUser {

    UserNotification saveUser(UserNotification userNotification);

    UserNotification getUserById(Long id);

    UserNotification getAllUserFollowers(Long userId);


}
