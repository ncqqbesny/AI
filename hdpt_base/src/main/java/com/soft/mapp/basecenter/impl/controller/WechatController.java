package com.soft.mapp.basecenter.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.soft.mapp.basecenter.controller.IWechatController;
import com.soft.mapp.basecenter.domain.Wechat.SubscribeVo;
import com.soft.mapp.basecenter.domain.Wechat.TemplateMsgVo;
import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IUserService;
import com.soft.mapp.basecenter.services.IWechatService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.soft.mapp.basecenter.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

@RestController
@Api(tags = "微信平台接口")
@RequestMapping("/wx")
public class WechatController implements IWechatController {
    private final static Logger log = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    IWechatService wechatService;
    @Autowired
    IUserService userService;
    @Value("${file.image.path}")
    public String imagePath;
    @Value("${http.base.pre.url}")
    public String httpPreUrl;

    @Override
    @ApiOperation(value = "微信小程序登录", notes = "微信小程序登录使用wx.login登录获得")
    @RequestMapping(value = "/small/onLogin", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 1) //排序
    @AutoLog(value = "微信小程序登录", operateType = 2, logType = 1, paramType = 2)
    public ServerResponse<?> getWxOnLogin(String code) {
        Map<String, Object> resultMap = wechatService.getWxSmallPrgLoginInfo(code);
        if (resultMap == null) {
            return ServerResponse.createByErrorMessage("登录错误");
        }
        return ServerResponse.createBySuccess(resultMap);
    }

    @Override
    @ApiOperation(value = "微信小程序手机快捷登录", notes = "微信小程序手机快捷登录")
    @RequestMapping(value = "/small/wxphonelogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    @AutoLog(value = "微信小程序手机快捷登录", operateType = 1, logType = 1, paramType = 0)
    public ServerResponse<?> wxPhoneLogin(@RequestBody Map params) {
        if (params == null) {
            return ServerResponse.createByErrorMessage("参数错误:没有传参");
        }
        String code = params.get("code").toString();
        String openid = params.get("openid").toString();
        String username = params.get("username").toString();
        String password = params.get("password").toString();
        if (StringUtil.isNotEmpty(code)) {
            if (StringUtil.isEmpty(openid)) {
                return ServerResponse.createByErrorMessage("参数错误:openid 没有值");
            }
        }
        if (StringUtil.isNotEmpty(username)) {
            if (StringUtil.isEmpty(password)) {
                return ServerResponse.createByErrorMessage("参数错误:password 没有值");
            }
        }
        Map<String, Object> resultMap = wechatService.getWxSmallPhoneLoginInfo(code, openid, username, password);
        if (resultMap == null || resultMap.size() == 0) {
            return ServerResponse.createByErrorMessage("没有获得手机信息");
        }
        return ServerResponse.createBySuccess(resultMap);
    }

    @Override
    @ApiOperation(value = "微信小程序获取二维码", notes = "微信小程序根据路径创建二维码")
    @RequestMapping(value = "/small/getWxSmallQRCode", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "路径地址，例如（/pages/match/index?id=1）", required = true, paramType = "Query", dataType = "String"),
            @ApiImplicitParam(name = "scene", value = "场景，只能为数字（暂无限制），空值则使有限制数量的二维码，可直接到对应的页面", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "width", value = "二维码宽度单位 px。最小 280px，最大 1280px;默认是430，", required = true, paramType = "Query", dataType = "Integer")
    })
    @ResponseBody
    @ApiOperationSupport(order = 1) //排序
    @AutoLog(value = "微信小程序登录", operateType = 2, logType = 1, paramType = 2)
    public ServerResponse<?> getWxSmallQRCode(String path, Integer scene, Integer width, HttpServletRequest request) {
        if (StringUtil.isEmpty(path)) {
            return ServerResponse.createByErrorMessage("参数错误:url,width 没有值");
        }
        String pathFileName = wechatService.getWxSmallQRCode(path, scene, width);
        if (StringUtil.isEmpty(pathFileName)) {
            return ServerResponse.createByErrorMessage("没有获得二维码信息");
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/images/";
        String resultImageUrl = basePath + pathFileName;
        return ServerResponse.createBySuccess(resultImageUrl);
    }

    @ResponseBody
    @ApiOperation(value = "微信程序上传", notes = "微信上传头像或其它信息等，目前支持头像上传")
    @RequestMapping(value = "/wxSubject", produces = "application/json", method = RequestMethod.POST)
    public ServerResponse<?> wxSubject(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile files) {
        String userId = "";
        String openId = "";
        UserVo userVo = new UserVo();
        try {
            userId = request.getParameter("userId");
            openId = request.getParameter("openId");
            log.info("openId:" + openId + "---userId:" + userId);
            userVo.setOpenId(openId);
            userVo.setUserId(ConvertUtils.toInt(userId));
        } catch (Exception e) {
            log.error("wxSubject--erorr" + e);
        }

        // 构建上传目录路径
        // request.getServletContext().getRealPath("/upload");

        String currentDirectoryPath = System.getProperty("user.dir");
        //你自己保存音频的URL;
        String subDirPath = imagePath + "/wxsubject";
        String uploadPath = currentDirectoryPath + subDirPath;
        // 如果目录不存在就创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        // 获取文件的 名称.扩展名
        String oldName = files.getOriginalFilename();
        String extensionName = "";
        // 获取原来的扩展名
        if ((oldName != null) && (oldName.length() > 0)) {
            int dot = oldName.lastIndexOf('.');
            if ((dot > -1) && (dot < (oldName.length() - 1))) {
                extensionName = oldName.substring(dot);
            }
        }
        // 构建文件名称
        String fileName = System.currentTimeMillis() + "_" + System.nanoTime()
                + extensionName;
        // 构建文件路径
        String filePath = uploadPath + File.separator + fileName;
        // 保存文件
        try {
            FileUtils.writeByteArrayToFile(new File(filePath),
                    files.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String wxImageUrl = httpPreUrl + subDirPath + File.separator + fileName;
        userVo.setWxHeadurl(wxImageUrl);
        if (userService.updateByExampleSelective(userVo) > 0) {
            return ServerResponse.createBySuccess(wxImageUrl);
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "微信小程序发订订阅消息", notes = "微信小程序发订订阅消息")
    @RequestMapping(value = "/small/wxSendSubscribeInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    @AutoLog(value = "微信小程序发订订阅消息", operateType = 1, logType = 1, paramType = 0)
    public ServerResponse<?> wxSendSubscribeInfo(@RequestBody SubscribeVo vo) {
        String msg = wechatService.wxSmarllSendSubscribeInfo(vo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "微信公众号发模板消息", notes = "微信公众号模板发送")
    @RequestMapping(value = "/public/wxPublicSendSubscribeInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    @AutoLog(value = "微信公众号发订订阅消息", operateType = 1, logType = 1, paramType = 0)
    public ServerResponse<?> wxPublicSendSubscribeInfo(@RequestBody TemplateMsgVo vo) {
        String msg = wechatService.wxPublicSendSubscribeInfo(vo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "同步公从号用户信息", notes = "同步公从号用户信息")
    @RequestMapping(value = "/public/SyncWxPublicUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    @AutoLog(value = "同步公从号用户信息", operateType = 1, logType = 1, paramType = 0)
    public ServerResponse<?> SyncWxPublicUser() {
        String msg = wechatService.syncWxPublicUser();
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
}
