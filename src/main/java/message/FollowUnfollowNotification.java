package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowUnfollowNotification {

    //who is going to be followed
    Long mainUserId;

    //who is the follower
    PostedBy follower;

}
