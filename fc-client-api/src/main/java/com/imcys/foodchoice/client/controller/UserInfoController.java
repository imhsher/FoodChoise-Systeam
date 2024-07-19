package com.imcys.foodchoice.client.controller;

import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.model.food.UserCollection;
import com.imcys.foodchoice.client.model.user.UserAttention;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.client.model.user.UserInfoAttention;
import com.imcys.foodchoice.client.service.IUserInfoService;
import com.imcys.foodchoice.client.service.auth.IUserTokenService;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.model.ResponseResult;
import com.imcys.foodchoice.common.utils.PasswordUtils;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/client/user")
public class UserInfoController extends BaseController {
    private final IUserInfoService userInfoService;
    private final IUserTokenService userTokenService;

    private final PasswordUtils passwordUtils = new PasswordUtils();

    private final static String LOGIN_TOKEN_KEY = "sx_token";

    public UserInfoController(IUserInfoService userInfoService, IUserTokenService userTokenService) {
        this.userInfoService = userInfoService;
        this.userTokenService = userTokenService;
    }

    @Data
    public static class RegUserInfo {
        @NotBlank(message = "用户名不可空")
        private String username;
        @NotBlank(message = "密码不可空")
        private String password;
        @NotBlank(message = "邮箱不可为空")
        private String email;
        @NotBlank(message = "邮箱验证码不可为空")
        private String emailCode;
        @NotBlank(message = "验证码不可为空")
        private String captchaCode;
    }


    @PostMapping("/regUser")
    public ResponseResult regUser(@RequestBody RegUserInfo regUserInfo) {

        Integer result = userInfoService.registerUser(regUserInfo);

        if (result < 0) return ResponseResult.failed(4000, "注册失败，请联系管理员");

        return ResponseResult.success("注册成功");

    }


    @Data
    public static class LoginInfo {
        private String username;
        private String email;
        @NotBlank(message = "密码不可空")
        private String password;
    }

    @PostMapping("/login")
    public ResponseResult login(HttpServletResponse response, @RequestBody LoginInfo loginInfo) {
        // 有异常会自行中断
        UserInfo userInfo = userInfoService.userLogin(loginInfo);
        String token = userTokenService.addUserToken(userInfo);

        //设置Token
        Cookie cookie = new Cookie(LOGIN_TOKEN_KEY, token);
        cookie.setPath("/");
        cookie.setMaxAge(2592000);
        response.addCookie(cookie);

        return ResponseResult.success("登录成功", userInfo);

    }


    @GetMapping("/info")
    @ClientAuth
    public ResponseResult getUserInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Optional<UserInfo> userInfo = userInfoService.getOptById(userId);
        if (userInfo.isEmpty()) return ResponseResult.error(4100, "获取失败");
        return ResponseResult.success("获取成功", userInfo);
    }

    @GetMapping("/publicInfo")
    public ResponseResult getUserInfo(Integer userId) {
        Optional<UserInfo> userInfo = userInfoService.getOptById(userId);
        if (userInfo.isEmpty()) return ResponseResult.error(4100, "获取失败");
        return ResponseResult.success("获取成功", userInfo);
    }

    @PutMapping("/updateUserInfo")
    @ClientAuth
    public ResponseResult updateUserInfo(@RequestBody @Validated UserInfo userInfo, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        userInfo.setId(userId);
        userInfoService.updateUserInfo(userInfo);
        return ResponseResult.success("修改成功");
    }


    @Data
    public static class UpAvatar {
        private String headPic;
    }


    @PutMapping("/upAvatar")
    @ClientAuth
    public ResponseResult upAvatar(@RequestBody UpAvatar upAvatar, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        userInfoService.upAvatar(upAvatar.getHeadPic(), userId);
        return ResponseResult.success("修改成功");
    }


    @DeleteMapping("/loginOut")
    public ResponseResult loginOut(@RequestParam Integer userId) {
        userInfoService.loginOut(userId);
        return ResponseResult.success("修改成功");
    }

/*
    点赞功能
    查找点赞状态及信息
*/
    @GetMapping("/getAttentionInfo")
    public ResponseResult getAttentionInfo(@RequestParam Integer userId){
        Optional<UserAttention>attentionInfo = userInfoService.getAttentionInfo(userId);
        return ResponseResult.success("查询成功", attentionInfo);
    }

//   修改点赞状态及数量
    @PostMapping("/updateAttentionStateAndNum")
    public ResponseResult updateAttentionStateSAndNum(@RequestParam Integer userId,@RequestParam Integer attentionState,@RequestParam Integer attentionNum){
        userInfoService.updateAttentionState(userId,attentionState,attentionNum);
        return ResponseResult.success("修改成功");
    }

//    查找所有被关注的用户信息
    @GetMapping("/shareAllAttUserInfo")
    public Map<String, Object> shareAllAttUserInfo(@RequestParam Integer userId){
        List<UserInfoAttention> attUserInfo = userInfoService.getAllUserInfo(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "请求成功");
        result.put("item", attUserInfo);
        return result;
    }


//    查找是否关注该用户
    @GetMapping("/shareAttUser")
    public ResponseResult shareAttUser(@RequestParam Integer userId, @RequestParam Integer attId){
        Optional<UserInfoAttention>attentionInfo = userInfoService.shareAttUserInfo(userId, attId);
        return ResponseResult.success("修改成功",attentionInfo);
    }

//  添加该用户收藏
    @PostMapping("/addUserAtt")
    public ResponseResult addUserAtt(@RequestParam Integer userId, @RequestParam Integer attId){
        userInfoService.addUserAtt(userId, attId);
        return ResponseResult.success("添加成功");
    }

//  取消关注
    @DeleteMapping("/delUserAtt")
    public ResponseResult delUserAtt(@RequestParam Integer userId, @RequestParam Integer attId){
        userInfoService.delUserAtt(userId,attId);
        return ResponseResult.success("删除成功");
    }

    @PostMapping("/upPersonalityTags")
    public ResponseResult upPersonalityTags(@RequestParam String tags, @RequestParam Integer userId){
        userInfoService.upPersonalityTags(tags, userId);
        return ResponseResult.success("修改成功");
    }
}
