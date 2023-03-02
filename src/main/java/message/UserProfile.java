package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends PostedBy {

    private Set<PostedBy> followers;

    private Set<PostedBy> followings;

    public UserProfile(Long id, String email, String username, String imageUrl, Set<PostedBy> followers, Set<PostedBy> followings) {
        super(id, email, username, imageUrl);
        this.followers = followers;
        this.followings = followings;
    }
}