package com.life.decision.support.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import com.life.decision.support.service.IQuestionnaireInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Controller
@RequestMapping("/questionAnswer")
public class QuestionAnswerController {

    @Autowired
    private IQuestionAnswerService questionAnswerService;
    @Autowired
    private IQuestionnaireSubmitInformationService questionnaireSubmitInformationService;
    @Autowired
    private IQuestionnaireGroupInformationService groupInformationService;
    @Autowired
    private IQuestionnaireInformationService questionnaireInformationService;

    /**
     * 新增保存
     *
     * @param obj
     * @return
     */
    @PostMapping("adminSaveNewQuestionnaire")
    @ResponseBody
    @Transactional
    public Object adminSaveNewQuestionnaire(@RequestBody JSONObject obj) {
        obj.putOpt("isAdmin", true);
        obj.putOpt("isSubmit", false);
        return saveBatch(obj);
    }

    /**
     * 提交场景：
     * 修改已有的问卷提交 -update update submitInfo
     * 新增提交    - save new submitInfo
     * 保存场景：
     * 修改已有的问卷保存 -update update submitInfo
     * 新增时保存 -save new submitInfo
     * <p>
     * 新增提交
     *
     * @param obj
     * @return
     */
    @PostMapping("adminSubmitNewQuestionnaire")
    @ResponseBody
    @Transactional
    public Object adminSubmitNewQuestionnaire(@RequestBody JSONObject obj) {
        obj.putOpt("isAdmin", true);
        return saveBatch(obj);
    }

    /**
     * 更新保存
     *
     * @param obj
     * @return
     */
    @PostMapping("adminSaveExistQuestionnaire")
    @ResponseBody
    @Transactional
    public Object adminSaveExistQuestionnaire(@RequestBody JSONObject obj) {
        obj.putOpt("isAdmin", true);
        obj.putOpt("isSubmit", false);
        return update(obj);
    }

    /**
     * 更新提交
     *
     * @param obj
     * @return
     */
    @PostMapping("adminSubmitExistQuestionnaire")
    @ResponseBody
    @Transactional
    public Object adminSubmitExistQuestionnaire(@RequestBody JSONObject obj) {
        obj.putOpt("isAdmin", true);
        return update(obj);
    }


    @PostMapping("save")
    @ResponseBody
    @Transactional
    public Object saveBatch(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list")
                .toList(QuestionAnswer.class);
        String userId = obj.getStr("userId");
        String questionnaireId = obj.getStr("questionnaireId");
        // 插入内容
        LocalDateTime now = LocalDateTime.now();
        String submitId = IdUtil.fastUUID();
        questionAnswerService.saveBatch(list, userId, questionnaireId, now, submitId);
        QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
        uploadAdminSubmitInfo(obj, submitInfo);
        submitInfo.setQuestionnaireId(questionnaireId);
        submitInfo.setUserId(userId);
        submitInfo.setCreateTime(now);
        submitInfo.setUpdateTime(now);
        submitInfo.setId(submitId);
        // 保存提交记录
        questionnaireSubmitInformationService.save(submitInfo);
        // 保存产品组记录
        String groupId = groupInformationService.save(userId, submitId, questionnaireId, now);
        // 提交修改结束后 都要返回 当前问卷组内 未完成的问卷
        return ResultUtils.returnSuccess(getQuestionnaireGroup(groupId));
    }

    private JSONObject getQuestionnaireGroup(String groupId) {
        QuestionnaireGroupInformation entity = new QuestionnaireGroupInformation();
        entity.setGroupId(groupId);
        List<SubmitOfTheQuestionnaireGroup> resultList = new ArrayList<>();
        List<String> submitList = groupInformationService.findList(entity)
                .stream().map(QuestionnaireGroupInformation::getQuestionnaireId)
                .collect(Collectors.toList());
        List<QuestionnaireInformation> questionnaireInformationList = questionnaireInformationService.findList(new QuestionnaireInformation());
        questionnaireInformationList.forEach(naire -> {
            SubmitOfTheQuestionnaireGroup temp = new SubmitOfTheQuestionnaireGroup();
            temp.setQuestionnaireId(naire.getId());
            temp.setQuestionnaireName(naire.getQuestionnaireTypeLabel());
            temp.setIsSubmit(submitList.contains(naire.getId()));
            resultList.add(temp);
        });
        JSONObject result = new JSONObject();
        result.putOnce("data", resultList);
        result.putOnce("finished", resultList.stream().filter(SubmitOfTheQuestionnaireGroup::getIsSubmit).count() == resultList.size());
        result.putOnce("groupId", groupId);
        return result;
    }

    @PostMapping("update")
    @ResponseBody
    @Transactional
    public Object update(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list").toList(QuestionAnswer.class);
        String submitId = obj.getStr("submitId");
        String userId = obj.getStr("userId");
        if (StrUtil.isBlank(submitId) || StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("submitId或userId为空");
        }
        if (CollUtil.isNotEmpty(list)) {
            QuestionnaireSubmitInformation submit = questionnaireSubmitInformationService.getById(submitId);
            questionAnswerService.updateBatch(list, submit);
            QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
            submitInfo.setId(submitId);
            uploadAdminSubmitInfo(obj, submitInfo);
            questionnaireSubmitInformationService.update(submitInfo);
            groupInformationService.updateBySubmit(submitId);
            String groupId = groupInformationService.getBySubmitId(submitId).getGroupId();
            return ResultUtils.returnSuccess(getQuestionnaireGroup(groupId));
        }
        return ResultUtils.returnSuccess();
    }

    private void uploadAdminSubmitInfo(JSONObject obj, QuestionnaireSubmitInformation submitInfo) {
        if (obj.getBool("isAdmin", false)) {
            submitInfo.setIsAdminSubmit(1);
        } else {
            submitInfo.setIsAdminSubmit(0);
        }
        // 如果isSubmit为false则设为保存状态，如果没有isSubmit或者为true则认为是提交状态
        if (!obj.getBool("isSubmit", true)) {
            submitInfo.setQuestionnaireStatus(0);
        } else {
            submitInfo.setQuestionnaireStatus(1);
        }
    }

}
