package com.cloudkeeper.leasing.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;

/**
 *  返回值对象
 * @param <T> 泛型
 * @author jerry
 */
@Getter
@Setter
@ToString
public class CloudResult<T> {

    /** 返回值编码*/
    private int code;

    /** 返回信息*/
    private String msg;

    /** 返回内容*/
    private T data;

    /** 返回信息*/
    private int status;

    private CloudResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CloudResult() {
    }

    @Nonnull
    public static <T> CloudResult<T> of(int code) {
        return new CloudResult<>(code, null, null);
    }

    @Nonnull
    public static <T> CloudResult<T> of(String msg) {
        return new CloudResult<>(ResultCode.OK.code, msg, null);
    }

    @Nonnull
    public static <T> CloudResult<T> of(T data) {
        return new CloudResult<>(ResultCode.OK.code, null, data);
    }

    @Nonnull
    public static <T> CloudResult<T> ofNotFound() {
        return new CloudResult<>(ResultCode.OK.code, ResponseMessageConstants.NOT_FOUND, null);
    }

    @Nonnull
    public static <T> CloudResult<T> ofLost() {
        return new CloudResult<>(ResultCode.FAIL.code, ResponseMessageConstants.NOT_FOUND, null);
    }

    @Nonnull
    public static <T> CloudResult<T> ofAddSuccess(T data) {
        return new CloudResult<>(ResultCode.OK.code, ResponseMessageConstants.ADD_SUCCESS, data);
    }

    @Nonnull
    public static <T> CloudResult<T> ofUpdateSuccess(T data) {
        return new CloudResult<>(ResultCode.OK.code, ResponseMessageConstants.UPDATE_SUCCESS, data);
    }

    @Nonnull
    public static <T> CloudResult<T> ofDeleteSuccess() {
        return new CloudResult<>(ResultCode.OK.code, ResponseMessageConstants.DELETE_SUCCESS, null);
    }

    @Nonnull
    public static <T> CloudResult<T> of(int code, String msg) {
        return new CloudResult<>(code, msg, null);
    }

    @Nonnull
    public static <T> CloudResult<T> of(int code, T data) {
        return new CloudResult<>(code, null, data);
    }

    @Nonnull
    public static <T> CloudResult<T> of(String msg, T data) {
        return new CloudResult<>(ResultCode.OK.code, msg, data);
    }

    @Nonnull
    public static <T> CloudResult<T> of(int code, String msg, T data) {
        return new CloudResult<>(code, msg, data);
    }

    @Getter
    public enum ResultCode {

        /** 登录失败*/
        LOGIN_FAIL(101, "登录失败"),

        /** 成功*/
        OK(200, "成功"),

        /** 失败*/
        FAIL(500, "失败"),

        /** 服务器错误*/
        SERVER_ERROR(500, "服务器错误"),

        ;
        /** 编码*/
        private int code;

        /** 描述*/
        private String msg;

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
