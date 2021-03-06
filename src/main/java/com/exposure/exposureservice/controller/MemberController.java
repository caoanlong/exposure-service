package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "MemberController", description = "会员管理")
@RestController
@RequestMapping("/admin/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "findAll", notes = "查询所有会员", response = Member.class, responseContainer = "List")
    @PreAuthorize("hasAnyAuthority('member', 'member:list')")
    @GetMapping("/findAll")
    public ResultBean<Object> findAll() {
        List<Member> list = memberService.findAll();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findList", notes = "根据分页查询会员列表", response = Member.class, responseContainer = "List")
    @PreAuthorize("hasAnyAuthority('member', 'member:list')")
    @GetMapping("/findList")
    public ResultBean<Object> findList(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "isActive", required = false) Integer isActive,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageBean<List<Member>> list = memberService.findList(userName, email, isActive, pageIndex, pageSize);
        for (Member member: list.getList() ) {
            String key = "isOnline:" + member.getId();
            String s = stringRedisTemplate.opsForValue().get(key);
            if (null != s) {
                member.setIsOnline(1);
            } else {
                member.setIsOnline(0);
            }
        }
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "findById", notes = "根据ID查询会员详情", response = Member.class)
    @PreAuthorize("hasAnyAuthority('member', 'member:view')")
    @GetMapping("/findById")
    public ResultBean<Object> findById(@RequestParam("id") Long id) {
        Member member = memberService.findById(id);
        return ResultUtils.success(member);
    }

    @ApiOperation(value = "add", notes = "添加会员")
    @PreAuthorize("hasAnyAuthority('member', 'member:add')")
    @PostMapping("/add")
    public ResultBean<Object> add(@RequestBody Member member) {
        memberService.insert(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "update", notes = "修改会员")
    @PreAuthorize("hasAnyAuthority('member', 'member:edit')")
    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody Member member) {
        memberService.update(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "del", notes = "删除会员")
    @PreAuthorize("hasAnyAuthority('member', 'member:del')")
    @PostMapping("/del")
    public ResultBean<Object> del(@RequestBody @ApiParam(name = "id", value = "id", required = true) Map<String, Long> map) {
        Long id = map.get("id");
        memberService.del(id);
        return ResultUtils.success();
    }
}
