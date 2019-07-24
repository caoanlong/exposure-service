package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.Member;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    List<Member> findAll();
    List<Member> findList(
            @Param("userName") String userName,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    Member findById(Long id);
    Long total(@Param("userName") String userName);
    void insert(Member member);
    void update(Member member);
    void del(Long id);

    List<Member> findByName(@Param("userName") String userName);
    List<Member> findByEmail(@Param("email") String email);

    Member findByNameAndPassword(
            @Param("userName") String userName,
            @Param("password") String password
    );
    Member findByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password
    );
}
