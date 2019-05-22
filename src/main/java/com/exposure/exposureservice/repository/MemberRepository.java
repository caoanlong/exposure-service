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
            @Param("mobile") String mobile,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    Member findById(Integer id);
    Long total(String mobile);
    void insert(Member member);
    void update(Member member);
    void del(Integer id);
}
