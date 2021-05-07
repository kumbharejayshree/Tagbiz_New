package com.tagloy.tagbiz.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pddumbre on 10/6/2017.
 */

public class NoticeBoard {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private Object message;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static  class Result {

        @SerializedName("venue_id")
        @Expose
        private Integer venueId;
        @SerializedName("is_black_board")
        @Expose
        private Integer isBlackBoard;

        @SerializedName("black_board_json")
        @Expose
        private String blackBoardJson;

        public Integer getVenueId() {
            return venueId;
        }

        public void setVenueId(Integer venueId) {
            this.venueId = venueId;
        }

        public Integer getIsBlackBoard() {
            return isBlackBoard;
        }

        public void setIsBlackBoard(Integer isBlackBoard) {
            this.isBlackBoard = isBlackBoard;
        }

        public String getBlackBoardJson() {
            return blackBoardJson;
        }

        public void setBlackBoardJson(String blackBoardJson) {
            this.blackBoardJson = blackBoardJson;
        }
    }

}
