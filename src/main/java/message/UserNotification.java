package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {

    private Long id;

    private String username;

    private String email;

    private String imageUrl;

    private Set<PostedBy> followers = new HashSet<>();

    private Set<PostedBy> following = new HashSet<>();

}
