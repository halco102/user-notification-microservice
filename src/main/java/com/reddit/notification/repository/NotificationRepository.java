package com.reddit.notification.repository;

import com.reddit.notification.model.Notification;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CouchbaseRepository<Notification, String> {

    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and userProfile.id = $1")
    List<Notification> getAllNotificationsByUserId(Long userId);

}
