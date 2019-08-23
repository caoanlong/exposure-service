package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.Member;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.entity.req.MemberDto;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.MailService;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.utils.JwtUtils;
import com.exposure.exposureservice.utils.ResultUtils;
import com.exposure.exposureservice.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(value = "AppMemberController", description = "会员管理")
@RestController
@RequestMapping("/app/member")
public class AppMemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String emailReg = "([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";

    private Integer EMAIL_EXPIRE_TIME = 24;

    @ApiOperation(value = "findByToken", notes = "根据TOKEN查询会员详情", response = Member.class)
    @GetMapping("/findByToken")
    public ResultBean<Object> findByToken(HttpServletRequest request) {
        Long id = Long.valueOf((String) request.getAttribute("memberId"));
        Member member = memberService.findById(id);
        return ResultUtils.success(member);
    }

    @ApiOperation(value = "update", notes = "修改会员")
    @PostMapping("/update")
    public ResultBean<Object> update(@RequestBody Member member) {
        memberService.update(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "getEmailCode", notes = "获取邮箱验证码")
    @GetMapping("/getEmailCode")
    public ResultBean<Object> getEmailCode(@RequestParam("email") String email) {
        String code = UUIDUtils.getUUID().substring(0, 4);
        // 开启线程发送邮件
        new Thread() {
            @Override
            public void run() {
                try {
                    sendCodeEmail(code, email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return ResultUtils.success();
    }

    @ApiOperation(value = "resetPassword", notes = "重置密码")
    @PostMapping("/resetPassword")
    @Transactional
    public ResultBean<Object> resetPassword(@RequestBody MemberDto memberDto) {
        String email = memberDto.getEmail();
        String code = memberDto.getCode();
        String password = memberDto.getPassword();

        if (StringUtils.isBlank(email))
            throw new CommonException(ErrorCode.EMAIL_NOTNULL);
        if (StringUtils.isBlank(code))
            throw new CommonException(ErrorCode.CODE_NOTNULL);
        if (StringUtils.isBlank(password))
            throw new CommonException(ErrorCode.PASSWORD_NOTNULL);
        if (!email.matches(this.emailReg))
            throw new CommonException(ErrorCode.EMAIL_ERROR);

        List<Member> members = memberService.findByEmail(email);
        if (null == members || members.size() == 0) {
            throw new CommonException(ErrorCode.EMAIL_NOT_EXIST);
        }

        String codeRedis = stringRedisTemplate.opsForValue().get(code);
        if (null == codeRedis)
            throw new CommonException(ErrorCode.VALID_EXPIRE);

        Member member = members.get(0);
        member.setPassword(password);
        memberService.update(member);
        return ResultUtils.success();
    }

    @ApiOperation(value = "active", notes = "激活会员")
    @GetMapping("/active")
    public void active(
            HttpServletResponse response,
            @RequestParam("id") String id,
            @RequestParam("code") String code
    ) throws IOException {
        if (StringUtils.isBlank(id))
            throw new CommonException(ErrorCode.ID_NOTNULL);
        if (StringUtils.isBlank(code))
            throw new CommonException(ErrorCode.CODE_NOTNULL);
        String idStr = stringRedisTemplate.opsForValue().get(code);
        if (null == idStr)
            throw new CommonException(ErrorCode.VALID_EXPIRE);
        Long memberId = Long.valueOf(id);
        memberService.active(memberId, 1);
        response.sendRedirect("https://m.exposures.xyz");
    }

    @ApiOperation(value = "login", notes = "会员登录")
    @PostMapping("/login")
    public ResultBean<Object> login(HttpServletResponse response, @RequestBody MemberDto memberDto) {
        String email = memberDto.getEmail();
        String password = memberDto.getPassword();

        if (StringUtils.isBlank(email))
            throw new CommonException(ErrorCode.EMAIL_NOTNULL);
        if (StringUtils.isBlank(password))
            throw new CommonException(ErrorCode.PASSWORD_NOTNULL);
        if (!email.matches(this.emailReg))
            throw new CommonException(ErrorCode.EMAIL_ERROR);

        Member member = memberService.findByEmailAndPassword(email, password);
        if (null == member) {
            throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
        }
        return authReturn(response, member.getId().toString());
    }

    @ApiOperation(value = "register", notes = "会员注册")
    @PostMapping("/register")
    @Transactional
    public ResultBean<Object> register(HttpServletResponse response, @RequestBody MemberDto memberDto) {
        String userName = memberDto.getUserName();
        String email = memberDto.getEmail();
        String password = memberDto.getPassword();

        if (StringUtils.isBlank(email))
            throw new CommonException(ErrorCode.EMAIL_NOTNULL);
        if (StringUtils.isBlank(password))
            throw new CommonException(ErrorCode.PASSWORD_NOTNULL);
        if (!email.matches(this.emailReg))
            throw new CommonException(ErrorCode.EMAIL_ERROR);

        List<Member> members = memberService.findByEmail(email);
        if (null != members && members.size() > 0) {
            throw new CommonException(ErrorCode.EMAIL_EXIST);
        }

        Member member1 = new Member();
        if (StringUtils.isNotBlank(userName)) {
            member1.setUserName(userName);
        } else {
            member1.setUserName(UUIDUtils.getUUID().substring(0, 6));
        }
        member1.setEmail(email);
        member1.setPassword(password);
        member1.setIsActive(0);
        memberService.insert(member1);
        String id = member1.getId().toString();
        // 开启线程发送邮件
        new Thread() {
            @Override
            public void run() {
                try {
                    sendEmail(id, email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return authReturn(response, id);
    }

    @ApiOperation(value = "sendEmail", notes = "发送邮件")
    @PostMapping("/sendEmail")
    public ResultBean<Object> sendEmail(HttpServletRequest request) {
        String id = (String) request.getAttribute("memberId");
        Member member = memberService.findById(Long.valueOf(id));
        String email = member.getEmail();
        // 开启线程发送邮件
        new Thread() {
            @Override
            public void run() {
                try {
                    sendEmail(id, email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return ResultUtils.success();
    }

    private void sendEmail(String id, String to) throws MessagingException {
        String code = UUIDUtils.getUUID();
        stringRedisTemplate.opsForValue().set(code, id);
        stringRedisTemplate.expire(code, EMAIL_EXPIRE_TIME, TimeUnit.HOURS);
        String htmlStr = "<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n"
                + "	<div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n"
                +"		<h3>欢迎使用来曝光吧</h3>\n"
                +"		<a href='http://api.exposures.xyz/app/member/active?id="+id+"&code="+code+"' \">http://api.exposures.xyz/app/member/active?id="+id+"&code="+code+"</a>"
                + "		<div\n" + "style=\"text-align: center; padding: 4px\">点击以上链接激活账号！</div>\n"
                + "	</div>\n"
                + "</body>";
        mailService.sendHtmlMail(to, "来曝光吧！请激活您的账号", htmlStr);
    }

    private void sendCodeEmail(String code, String to) throws MessagingException {
        stringRedisTemplate.opsForValue().set(code, "1");
        stringRedisTemplate.expire(code, 1, TimeUnit.HOURS);
        String htmlStr = "<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n"
                +"<h3>验证码："+code+"</h3>\n"
                + "</body>";
        mailService.sendHtmlMail(to, "来曝光吧！验证码", htmlStr);
    }

    private ResultBean<Object> authReturn(HttpServletResponse response, String memberId) {
        String jwt = jwtUtils.createJWT(Constant.JWT_ID, "nimadebi1234fuck", memberId, Constant.JWT_TTL);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", jwt);
        return ResultUtils.success();
    }
}
