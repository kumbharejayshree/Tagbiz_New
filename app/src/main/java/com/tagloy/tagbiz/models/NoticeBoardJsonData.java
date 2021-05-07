package com.tagloy.tagbiz.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NoticeBoardJsonData {
    @SerializedName("board_color")
    @Expose
    private String boardColor;
    @SerializedName("oname")
    @Expose
    private String oname;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("footer")
    @Expose
    private String footer;
    @SerializedName("image")
    @Expose
    private String image;

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getBoardColor() {
        return boardColor;
    }

    public void setBoardColor(String boardColor) {
        this.boardColor = boardColor;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


    public String getFooter() {
        return footer;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public class Content {

        @SerializedName("content1")
        @Expose
        private String content1;
        @SerializedName("content2")
        @Expose
        private String content2;
        @SerializedName("content3")
        @Expose
        private String content3;
        @SerializedName("content4")
        @Expose
        private String content4;
        @SerializedName("content5")
        @Expose
        private String content5;
        @SerializedName("content6")
        @Expose
        private String content6;
        @SerializedName("content7")
        @Expose
        private String content7;
        @SerializedName("content8")
        @Expose
        private String content8;

        public String getContent6() {
            return content6;
        }

        public void setContent6(String content6) {
            this.content6 = content6;
        }

        public String getContent7() {
            return content7;
        }

        public void setContent7(String content7) {
            this.content7 = content7;
        }

        public String getContent8() {
            return content8;
        }

        public void setContent8(String content8) {
            this.content8 = content8;
        }

        public String getContent1() {
            return content1;
        }

        public void setContent1(String content1) {
            this.content1 = content1;
        }

        public String getContent2() {
            return content2;
        }

        public void setContent2(String content2) {
            this.content2 = content2;
        }

        public String getContent3() {
            return content3;
        }

        public void setContent3(String content3) {
            this.content3 = content3;
        }

        public String getContent4() {
            return content4;
        }

        public void setContent4(String content4) {
            this.content4 = content4;
        }

        public String getContent5() {
            return content5;
        }

        public void setContent5(String content5) {
            this.content5 = content5;
        }

    }
}
