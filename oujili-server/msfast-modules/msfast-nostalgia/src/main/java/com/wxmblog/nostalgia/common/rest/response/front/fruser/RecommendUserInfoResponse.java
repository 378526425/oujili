package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.UserArticleViewResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-08 16:16
 **/

@Data
public class RecommendUserInfoResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    private Integer height;

    @ApiModelProperty(value = "星座")
    private String constellation;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    private String profession;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" + "    FEMALE(\"女\")")
    private GenderEnum gender;

    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校")
    private String school;

    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历 Specialty(\"专科\"),\n" + "    Undergraduate(\"本科\"),\n" + "    Master(\"硕士\"),\n" + "    Doctor(\"博士\")")
    private HighestEducationEnum education;

    /**
     * @Description: 学历类型
     */
    @ApiModelProperty(value = "学历类型 FullTime(\"全日制\"),\n" + "    NOFullTime(\"非全日制\")")
    private EducationalTypeEnum educationalType;

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
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况 Unmarried(\"未婚\"),\n" + "    Divorce(\"离异\"),\n" + "    Widow(\"丧偶\")")
    private MaritalStatusEnum marriage;

    /**
     * 感情状况
     */
    @ApiModelProperty(value = "感情状况 Single(\"单身\"),\n" + "    Love(\"恋爱\")")
    private EmotionalStatus emotional;

    /**
     * 年薪
     */
    @ApiModelProperty(value = "年薪 Less_Ten(\"小于10w\"),\n" + "    Ten_Two(\"10w到20w\"),\n" + "    Two_Three(\"20w到30w\"),\n" + "    Three_Five(\"30w到50w\"),\n" + "    Five_Hundred(\"50w到100w\"),\n" + "    Greater_Hundred(\"大于100w\"),\n" + "    Secrecy(\"保密\")")
    private AnnualSalaryEnum annualSalary;

    /**
     * 相册
     */
    @ApiModelProperty(value = "相册")
    private List<String> imgList;


    @ApiModelProperty(value = "性格测试")
    private CharacterTypeResponse characterType;

    @ApiModelProperty(value = "剩余推荐次数")
    private Integer surplusNum;

    private Date birthday;

    @ApiModelProperty(value = "用户认证状态 IdentityAuth(\"实名认证\"),\n" + "    EducationAuth(\"学历认证\"),\n" + "    DOUBLE(\"双重认证\")")
    private UserAuthStatusEnum userAuthStatus;

    private AdditionalResponse additional;

    @ApiModelProperty(value = "朋友圈概览信息")
    private UserArticleViewResponse userArticleViewResponse;
}
