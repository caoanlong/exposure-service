package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.repository.MemberRepository;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.MD5Utils;
import com.exposure.exposureservice.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public PageBean<List<Member>> findList(
            String userName,
            String email,
            Integer isActive,
            Integer pageIndex,
            Integer pageSize
    ) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Member> members = memberRepository.findList(userName, email, isActive, pageStart, pageSize);
        Long total = memberRepository.total(userName, email, isActive);
        PageBean<List<Member>> pageBean = new PageBean<>();
        pageBean.setList(members);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    @Transactional
    public void insert(Member member) {
        Long id = snowFlake.nextId();
        member.setId(id);
        member.setCreateTime(new Date());
        memberRepository.insert(member);
    }

    @Override
    @Transactional
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    @Transactional
    public void del(Long id) {
        memberRepository.del(id);
    }

    @Override
    public Long total(String userName, String email, Integer isActive) {
        return memberRepository.total(userName, email, isActive);
    }

    @Override
    public void active(Long id, Integer isActive) {
        Member member = new Member();
        member.setId(id);
        member.setIsActive(isActive);
        memberRepository.update(member);
    }

    @Override
    public List<Member> findByName(String userName) {
        return memberRepository.findByName(userName);
    }

    @Override
    public List<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member findByNameAndPassword(String userName, String password) {
        String pwd = MD5Utils.md5(password, Constant.MD5_SALT);
        return memberRepository.findByNameAndPassword(userName, pwd);
    }

    @Override
    public Member findByEmailAndPassword(String email, String password) {
        String pwd = MD5Utils.md5(password, Constant.MD5_SALT);
        return memberRepository.findByEmailAndPassword(email, pwd);
    }
}
