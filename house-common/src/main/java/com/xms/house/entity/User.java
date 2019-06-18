package com.xms.house.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.xms.house.util.BaseEntity;

/**
 * 实现序列化
 * @author xie
 *
 */
public class User extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String phone;

    private String email;

    private String aboutme;

    private String passwd;

    private String avatar;

    private int type;

    private Date createTime;

    private int enable;

    private Integer agencyId;
    
    private String confirmPasswd;
    
    private MultipartFile avatarFile;
    
    private String agencyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme == null ? null : aboutme.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

	public String getConfirmPasswd() {
		return confirmPasswd;
	}

	public void setConfirmPasswd(String confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}

	public MultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
}