package com.wxmblog.nostalgia.common.rest.request.fruser;

import com.wxmblog.base.auth.common.rest.request.RegisterRequest;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.EducationalTypeEnum;
import com.wxmblog.nostalgia.common.enums.user.HighestEducationEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-07 15:48
 **/

@Data
public class AppletRegisterRequest extends RegisterRequest {

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @NotBlank
    private String headPortrait;

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
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lon;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

}
