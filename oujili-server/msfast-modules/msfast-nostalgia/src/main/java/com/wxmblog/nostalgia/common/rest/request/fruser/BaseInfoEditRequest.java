package com.wxmblog.nostalgia.common.rest.request.fruser;

import com.wxmblog.nostalgia.common.enums.user.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class BaseInfoEditRequest {

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @Length(min = 1, max = 8)
    private String nickName;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    @Min(value = 140)
    @Max(value = 220)
    private Integer height;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    @Size(min = 1, max = 10)
    private String city;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    @Size(min = 1, max = 10)
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
    @Size(min = 1, max = 50)
    private String weChatNumber;


    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校")
    @Size(min = 1, max = 30)
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


    /**
     * 关于我
     */
    @ApiModelProperty(value = "关于我")
    @Size(min = 10, max = 200)
    private String aboutMe;

    /**
     * 兴趣爱好
     */
    @ApiModelProperty(value = "兴趣爱好")
    @Size(min = 10, max = 200)
    private String interest;

    /**
     * 择偶要求
     */
    @ApiModelProperty(value = "心议的他")
    @Size(min = 10, max = 200)
    private String loveRequirement;


}
