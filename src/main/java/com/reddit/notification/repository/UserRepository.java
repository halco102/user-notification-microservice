package com.reddit.notification.repository;

import message.UserNotification;
import message.UserProfile;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<UserProfile, String> {


}
