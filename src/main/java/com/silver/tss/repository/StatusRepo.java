package com.silver.tss.repository;

import com.silver.tss.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by lynch on 2018/10/23. <br>
 **/
@Repository
public interface StatusRepo extends JpaRepository<Status, Integer> {
}
