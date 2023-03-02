package com.reddit.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import message.PostDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPostNotification {

    private PostDto postDto;

}
