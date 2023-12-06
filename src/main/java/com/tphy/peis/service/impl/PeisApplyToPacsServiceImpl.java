package com.tphy.peis.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tphy.peis.entity.dto.PacsItemDTO;
import com.tphy.peis.mapper.peisReport.PeisApplyToPacsMapper;
import com.tphy.peis.service.PacsPdfToJpgService;
import com.tphy.peis.service.PeisApplyToPacsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PeisApplyToPacsServiceImpl implements PeisApplyToPacsService {

    @Resource
    PeisApplyToPacsMapper peisApplyToPacsMapper;
    @Override
    public Boolean applyToAdd(String examNum) {
        examNum = (String) JSONUtil.parseObj(examNum).get("examNum");
        Boolean insertFlag = true;
        String url = "http://192.168.88.29:1314/frontend/accessToken/create";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secret", "BnoyKWy04sJ5LvnXZ+/YA9rTCf6/kYI5rirE6ENcFmC+cuuLPAVPti6miCC8P=");
        HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .execute();
        JSONObject jsonResult = new JSONObject(response.body());
        String token = jsonResult.getJSONObject("data").getStr("token");
        System.out.println(token);
        List<PacsItemDTO> items = peisApplyToPacsMapper.getPacsItems(examNum);
        String insertUrl = "http://192.168.88.29:1314/frontend/accessToken/create";

        for (PacsItemDTO item : items) {
            //item.setStatus("0");
            String jsonPayload = JSONUtil.toJsonStr(item);
           // 创建HTTP请求对象并设置负载和URL
            HttpResponse response1 = HttpRequest.post(url)
                    .header("Content-Type", "application/json")
                    .header("Authorization",token)
                    .body(jsonPayload)
                    .execute();

            JSONObject insertResult = new JSONObject(response.body());
            String isInsert = insertResult.getStr("data");
            if(isInsert.equals("成功")){
                peisApplyToPacsMapper.deleteExamItemTrans(item.getPeisNo(),item.getItemId());
                peisApplyToPacsMapper.insertExamItemTrans(item.getPeisNo(),item.getItemId(), item.getItemCode());
            }else{
                log.info(item.getItemName()+"项目 "+item.getItemCode()+" 插入失败");
                insertFlag = false;
            }
        }
        return insertFlag;
    }
}
