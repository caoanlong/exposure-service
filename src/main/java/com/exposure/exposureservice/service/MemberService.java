package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;

import java.util.List;

public interface MemberService {
    List<Member> findAll();
    PageBean<List<Member>> findList(String userName, Integer pageIndex, Integer pageSize);
    Member findById(Long id);
    void insert(Member member);
    void update(Member member);
    void del(Long id);

    List<Member> findByName(String userName);
    Member findByNameAndPassword(String userName, String password);
}
