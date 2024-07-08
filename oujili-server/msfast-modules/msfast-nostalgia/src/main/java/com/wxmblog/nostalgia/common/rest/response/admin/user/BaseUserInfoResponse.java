package com.wxmblog.nostalgia.common.rest.response.admin.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-22 16:20
 **/

@Data
public class BaseUserInfoResponse {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户状态 启用/停用/注销
     */
    @ApiModelProperty(value = "用户状态 ENABLE(\"启用\"),\n" +
            "    DISABLE(\"禁用\"),\n" +
            "    LOGOFF(\"注销\")")
    private FrUserStatusEnum status;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;


    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型 Dummy(\"虚拟\"),\n" +
            "    Normal(\"正常\")")
    private UserTypeEnum userType;


    @ApiModelProperty(value = "最近活跃时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date latelyTime;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    private Integer height;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    private String profession;

    /**
     * 感情状况
     */
    @ApiModelProperty(value = "感情状况 Single(\"单身\"),\n" +
            "    Love(\"恋爱\")")
    private EmotionalStatus emotional;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况 Unmarried(\"未婚\"),\n" +
            "    Divorce(\"离异\"),\n" +
            "    Widow(\"丧偶\")")
    private MaritalStatusEnum marriage;

    /**
     * 脱单目标
     */
    @ApiModelProperty(value = "脱单目标 One_Year(\"一年内\"),\n" +
            "    Two_Year(\"两年内\"),\n" +
            "    Any_Time(\"随时\"),\n" +
            "    Un_known(\"暂不考虑\")")
    private LoveGoalEnum loveGoal;

    /**
     * 年薪
     */
    @ApiModelProperty(value = "年薪 Less_Ten(\"小于10w\"),\n" +
            "    Ten_Two(\"10w到20w\"),\n" +
            "    Two_Three(\"20w到30w\"),\n" +
            "    Three_Five(\"30w到50w\"),\n" +
            "    Five_Hundred(\"50w到100w\"),\n" +
            "    Greater_Hundred(\"大于100w\"),\n" +
            "    Secrecy(\"保密\")")
    private AnnualSalaryEnum annualSalary;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String weChatNumber;


    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校")
    private String school;

    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历 Specialty(\"专科\"),\n" +
            "    Undergraduate(\"本科\"),\n" +
            "    Master(\"硕士\"),\n" +
            "    Doctor(\"博士\")")
    private HighestEducationEnum education;

    /**
     * @Description: 学历类型
     */
    @ApiModelProperty(value = "学历类型 FullTime(\"全日制\"),\n" +
            "    NOFullTime(\"非全日制\")")
    private EducationalTypeEnum educationalType;


    @ApiModelProperty(value = "星座")
    private String constellation;

    /**
     * 关于我
     */
    @ApiModelProperty(value = "关于我")
    private String aboutMe;

    /**
     * 兴趣爱好
     */
    @ApiModelProperty(value = "兴趣爱好")
    private String interest;

    /**
     * 择偶要求
     */
    @ApiModelProperty(value = "心议的他")
    private String loveRequirement;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 金币余额
     */
    @ApiModelProperty(value = "金币余额")
    private Integer goldBalance;

}
