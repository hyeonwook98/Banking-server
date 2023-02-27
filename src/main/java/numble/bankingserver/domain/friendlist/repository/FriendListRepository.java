package numble.bankingserver.domain.friendlist.repository;

import numble.bankingserver.domain.friendlist.entity.FriendList;
import numble.bankingserver.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    Optional<FriendList> findByHostUserAndFriendUser(User hostUser, User friendUser);
    @Query("select f.friendUser from FriendList f where f.hostUser = :hostUser")
    Page<User> findFriendUser(@Param("hostUser") User hostUser, Pageable pageable);
    @Modifying
    @Transactional
    @Query("delete from FriendList f where (f.hostUser = :hostUser and f.friendUser = :friendUser) or " +
            "(f.hostUser = :friendUser and f.friendUser = :hostUser)")
    void deleteFriendList(@Param("hostUser") User hostUser, @Param("friendUser") User friendUser);
}
