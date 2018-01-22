package vn.fpt.fsoft.stu.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.fsoft.stu.auth.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
