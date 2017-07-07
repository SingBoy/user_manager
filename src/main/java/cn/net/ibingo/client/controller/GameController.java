package cn.net.ibingo.client.controller;


import cn.net.ibingo.common.controller.BaseController;
import cn.net.ibingo.common.utils.ConstantConfig;
import cn.net.ibingo.common.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

@Controller
@RequestMapping("/game")
public class GameController extends BaseController {


    /**
     * 根据用户名称查询安装的游戏
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findInstallGams", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray findInstallGams(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpUtil httpUtil = new HttpUtil();
        String username = request.getParameter("userName");
        if (!StringUtils.isEmpty(username)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", username);
            String sr = null;
            try {
                sr = httpUtil.sendHttpClientGet(ConstantConfig.GAME_CENTER_URL+"",params,null);
                if(!StringUtils.isEmpty(sr)) {
                    JSONObject jsStr = JSONObject.fromObject(sr);
                    if (jsStr != null) {
                        JSONArray jsonArray = JSONArray.fromObject(jsStr.get(""));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                resultMap.put("status", ConstantConfig.HTTP_CODE_1003);
                return JSONArray.fromObject(resultMap);
            }

        }else{
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
            return JSONArray.fromObject(resultMap);
        }
        return new JSONArray();
    }

}
