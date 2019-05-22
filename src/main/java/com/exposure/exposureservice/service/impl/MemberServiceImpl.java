package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.repository.MemberRepository;
import com.exposure.exposureservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public PageBean<List<Member>> findList(String mobile, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Member> members = memberRepository.findList(mobile, pageStart, pageSize);
        Long total = memberRepository.total(mobile);
        PageBean<List<Member>> pageBean = new PageBean<>();
        pageBean.setList(members);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public Member findById(Integer id) {
        return memberRepository.findById(id);
    }

    @Override
    public void insert(Member member) {
        member.setCreateTime(new Date());
        memberRepository.insert(member);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public void del(Integer id) {
        memberRepository.del(id);
    }
}
