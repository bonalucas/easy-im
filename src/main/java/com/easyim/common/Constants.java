package com.easyim.common;

/**
 * 枚举信息定义类
 *
 * @author 单程车票
 */
public class Constants {

    /**
     * 对话类型状态码
     */
    public enum DialogType {

        SINGLE_CHAT(0, "单聊"),
        GROUP_CHAT(1, "群聊");

        private Integer code;
        private String status;

        DialogType(Integer code, String status) {
            this.code = code;
            this.status = status;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    /**
     * 用户类型状态码
     */
    public enum UserStatus {

        ADDED(0, "已添加"),
        UN_ADD(1, "未添加");

        private Integer code;
        private String status;

        UserStatus(Integer code, String status) {
            this.code = code;
            this.status = status;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
