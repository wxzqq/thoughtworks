package com.thoughtworks.chengdu.gb.moments.model;

import com.thoughtworks.chengdu.gb.moments.base.BaseModel;

/**
 * Created by GB on 2017/11/27.
 */

public class UserInfoModel extends BaseModel {
    private UserInfo Content;

    public UserInfo getContent() {
        return Content;
    }

    public void setContent(UserInfo content) {
        Content = content;
    }

    public static class UserInfo {
        private String RefreshToken;
        private String Token;
        private String IdentityCode;
        private String userId;//登录账号
        private String Name;
        private String userPassword;
        private String Id;
        private String IDCardNo;
        private String Empno;
        private String Sex;
        private String OrganizationsId;
        private String OrganizationsName;
        private String PositionName;
        private String PositionId;
        private String BirthDate;
        private String Images;
        private String PoliticalStatus;//政治面貌
        private String userType;//用户类型

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getRefreshToken() {
            return RefreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            RefreshToken = refreshToken;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

        public String getIdentityCode() {
            return IdentityCode;
        }

        public void setIdentityCode(String identityCode) {
            IdentityCode = identityCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getIDCardNo() {
            return IDCardNo;
        }

        public void setIDCardNo(String IDCardNo) {
            this.IDCardNo = IDCardNo;
        }

        public String getEmpno() {
            return Empno;
        }

        public void setEmpno(String empno) {
            Empno = empno;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getOrganizationsId() {
            return OrganizationsId;
        }

        public void setOrganizationsId(String organizationsId) {
            OrganizationsId = organizationsId;
        }

        public String getOrganizationsName() {
            return OrganizationsName;
        }

        public void setOrganizationsName(String organizationsName) {
            OrganizationsName = organizationsName;
        }

        public String getPositionName() {
            return PositionName;
        }

        public void setPositionName(String positionName) {
            PositionName = positionName;
        }

        public String getPositionId() {
            return PositionId;
        }

        public void setPositionId(String positionId) {
            PositionId = positionId;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String birthDate) {
            BirthDate = birthDate;
        }

        public String getImages() {
            return Images;
        }

        public void setImages(String images) {
            Images = images;
        }

        public String getPoliticalStatus() {
            return PoliticalStatus;
        }

        public void setPoliticalStatus(String politicalStatus) {
            PoliticalStatus = politicalStatus;
        }
    }
}
