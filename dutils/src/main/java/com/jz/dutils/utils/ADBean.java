package com.jz.dutils.utils;

import java.util.List;

public class ADBean {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * code : 200
     * message :
     * data : {"adtercount":1,"adtab":[{"aderid":"2","status":"1","parameter":"{\"app_id\":\"\",\"param_id\":\"ca-app-pub-3940256099942544/6300978111\"}","weights":"100","position":"banner","channel":"HMS-Global","ad":"google","show":"1"}]}
     */

    private String code;
    private String message;
    private DataBean data;

    public static class DataBean {
        public int getAdtercount() {
            return adtercount;
        }

        public void setAdtercount(int adtercount) {
            this.adtercount = adtercount;
        }

        public List<AdtabBean> getAdtab() {
            return adtab;
        }

        public void setAdtab(List<AdtabBean> adtab) {
            this.adtab = adtab;
        }

        /**
         * adtercount : 1
         * adtab : [{"aderid":"2","status":"1","parameter":"{\"app_id\":\"\",\"param_id\":\"ca-app-pub-3940256099942544/6300978111\"}","weights":"100","position":"banner","channel":"HMS-Global","ad":"google","show":"1"}]
         */

        private int adtercount;
        private List<AdtabBean> adtab;

        public static class AdtabBean {
            /**
             * aderid : 2
             * status : 1
             * parameter : {"app_id":"","param_id":"ca-app-pub-3940256099942544/6300978111"}
             * weights : 100
             * position : banner
             * channel : HMS-Global
             * ad : google
             * show : 1
             */

            private String aderid;

            public String getAderid() {
                return aderid;
            }

            public void setAderid(String aderid) {
                this.aderid = aderid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public int getWeights() {
                return weights;
            }

            public void setWeights(int weights) {
                this.weights = weights;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getAd() {
                return ad;
            }

            public void setAd(String ad) {
                this.ad = ad;
            }

            public String getShow() {
                return show;
            }

            public void setShow(String show) {
                this.show = show;
            }

            private int status;
            private String parameter;
            private int weights;
            private String position;
            private String channel;
            private String ad;
            private String show;
        }
    }
}
