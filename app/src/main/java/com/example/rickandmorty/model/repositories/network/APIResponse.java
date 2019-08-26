package com.example.rickandmorty.model.repositories.network;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Rohin on 5/6/2019 @4:47 AM.
 */
public class APIResponse<T> {

    private boolean hasError;
    private String errorMsg;
    private T returnData;

    private boolean running = true;
    private boolean success = false;
    private boolean empty = false;


    private APIResponse(@Nullable T data, boolean hasError, @Nullable String message, boolean running, boolean success, boolean empty) {
        this.returnData = data;
        this.errorMsg = message;
        this.hasError = hasError;
        this.running = running;
        this.success = success;
        this.empty = empty;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }


    public static <T> APIResponse<T> success(@NonNull T data) {
        return new APIResponse<T>(data, false, null, false, true,false);
    }

    public static <T> APIResponse<T> error(String msg, @Nullable T data) {
        return new APIResponse<T>(data, true, msg, false, false,false);
    }

    public static <T> APIResponse<T> empty(T data) {
        return new APIResponse<T>(data, false, null, false, false,true);
    }

    public static <T> APIResponse<T> loading(@Nullable T data) {
        return new APIResponse<T>(data, false, null, true, false,false);
    }
}
