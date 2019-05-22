package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    List<Member> findAll();
    PageBean<List<Member>> findList(String mobile, Integer pageIndex, Integer pageSize);
    Member findById(Integer id);
    void insert(Member member);
    void update(Member member);
    void del(Integer id);
}
