package com.wxmblog.nostalgia.common.rest.request.admin.user;

import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.base.file.annotation.FileListSave;
import com.wxmblog.nostalgia.common.enums.user.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-03 14:26
 **/
@Data
public class UserAdminInfoAddRequest {

    @ApiModelProperty(value = "id 新增时为空")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @Length(min = 1, max = 8)
    @NotBlank
    private String nickName;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @Past(message = "出生日期必须是过去")
    @NotNull(message = "出生日期不可为空")
    private Date birthday;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    @NotNull
    private Integer height;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    @NotBlank
    private String profession;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    @NotNull
    private GenderEnum gender;

    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校")
    @NotBlank
    private String school;

    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历 Specialty(\"专科\"),\n" +
            "    Undergraduate(\"本科\"),\n" +
            "    Master(\"硕士\"),\n" +
            "    Doctor(\"博士\")")
    @NotNull
    private HighestEducationEnum education;

    /**
     * @Description: 学历类型
     */
    @ApiModelProperty(value = "学历类型 FullTime(\"全日制\"),\n" +
            "    NOFullTime(\"非全日制\")")
    @NotNull
    private EducationalTypeEnum educationalType;

    /**
     * 关于我
     */
    @ApiModelProperty(value = "关于我")
    @NotBlank
    @Size(min = 10,max = 200)
    private String aboutMe;

    /**
     * 兴趣爱好
     */
    @ApiModelProperty(value = "兴趣爱好")
    @NotBlank
    @Size(min = 10,max = 200)
    private String interest;

    /**
     * 择偶要求
     */
    @ApiModelProperty(value = "择偶要求")
    @NotBlank
    @Size(min = 10,max = 200)
    private String loveRequirement;


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
     * 感情状况
     */
    @ApiModelProperty(value = "感情状况 Single(\"单身\"),\n" +
            "    Love(\"恋爱\")")
    private EmotionalStatus emotional;

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
     * 相册
     */
    @ApiModelProperty(value = "相册")
    @FileListSave
    @NotEmpty
    private List<String> imgList;

    /**
     * 用户状态 启用/停用/注销
     */
    @ApiModelProperty(value = "用户状态 ENABLE(\"启用\"),\n" +
            "    DISABLE(\"禁用\")")
    private FrUserStatusEnum status;
}
