package com.canhhocit.learn01.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canhhocit.learn01.Entities.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,String>{
    boolean existsByUsername(String username);
    Account findByUsername(String username);
    void deleteByUsername(String username);

    //helpful matching pass
    // Optional<Account>findByUsername(String username);
}
