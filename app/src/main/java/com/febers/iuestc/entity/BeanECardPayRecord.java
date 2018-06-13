/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

import java.util.List;

/**
 * {"data":{"total_consume":"352.42","total_recharge":"400.00","consumes":[{"transtime":"2018-04-08 12:08:00",
 * "aftbala":"59.99","amount":"-8.80","position":"\u4e09\u98df\u58022FPOS39#","type":"POS\u6d88\u8d39","befbala":"68.79"},
 * {"transtime":"2018-04-08 10:09:50","aftbala":"68.79","amount":"-1.50","position":"\u7855\u58eb21\u680b\u8d85\u5e02POS4\u65b0\u589e",
 * "type":"POS\u6d88\u8d39","befbala":"70.29"},
 * {"transtime":"2018-04-07 20:40:23","aftbala":"70.29","amount":"-0.90","position":"\u672c21#\u697c5F\u6dcb040#000BBEE6",
 * "type":"\u6c34\u63a7\u6d88\u8d39","befbala":"71.19"},
 * {"transtime":"2018-04-07 17:59:54","aftbala":"71.19","amount":"-2.60","position":"\u7855\u58eb21\u680b\u8d85\u5e02POS4\u65b0\u589e",
 * "type":"POS\u6d88\u8d39","befbala":"73.79"},
 * {"transtime":"2018-04-07 12:30:08","aftbala":"74.79","amount":"-1.50","position":"\u7855\u58eb21\u680b\u8d85\u5e02POS4\u65b0\u589e",
 * "type":"POS\u6d88\u8d39","befbala":"76.29"},
 * {"transtime":"2018-04-07 12:05:26","aftbala":"76.29","amount":"-1.10","position":"\u672c21#\u697c5F\u6dcb040#000BBEE6",
 * "type":"\u6c34\u63a7\u6d88\u8d39","befbala":"77.39"},
 * {"transtime":"2018-04-05 17:47:12","aftbala":"77.39","amount":"-1.00","position":"\u672c21#\u697c5F\u6dcb040#000BBEE6",
 * "type":"\u6c34\u63a7\u6d88\u8d39","befbala":"78.39"},
 * {"transtime":"2018-04-04 12:12:30","aftbala":"78.39","amount":"-5.60","position":"\u4e09\u98df\u58022FPOS39#",
 * "type":"POS\u6d88\u8d39","befbala":"83.99"},
 * {"transtime":"2018-04-04 08:09:52","aftbala":"83.99","amount":"-5.50","position":"\u7855\u58eb21\u680b\u8d85\u5e02POS4\u65b0\u589e",
 * "type":"POS\u6d88\u8d39","befbala":"89.49"},
 * {"transtime":"2018-04-03 20:05:52","aftbala":"89.49","amount":"-0.70","position":"\u672c21#\u697c5F\u6dcb040#000BBEE6",
 * "type":"\u6c34\u63a7\u6d88\u8d39","befbala":"90.19"}]},"retcode":"0000","retmsg":""}

 */
public class BeanECardPayRecord {
    private String retcode;
    private String retmsg;
    private data data;

    public BeanECardPayRecord.data getData() {
        return data;
    }

    public String getRetcode() {
        return retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public static class data{
        private String total_consume;
        private String total_recharge;
        private List<consumes> consumesList;

        public List<BeanECardPayRecord.data.consumes> getConsumesList() {
            return consumesList;
        }

        public String getTotal_consume() {
            return total_consume;
        }

        public String getTotal_recharge() {
            return total_recharge;
        }

        public static class consumes{
            private String transtime;
            private String aftbala;
            private String amount;
            private String position;
            private String type;
            private String befbala;
//            public consumes(String transtime, String aftbala, String amount, String position, String type, String befbala) {
//                this.transtime = transtime;
//                this.aftbala = aftbala;
//                this.amount = amount;
//                this.position = position;
//                this.type = type;
//                this.befbala = befbala;
//            }
            public void setTranstime(String transtime) {
                this.transtime = transtime;
            }

            public void setAftbala(String aftbala) {
                this.aftbala = aftbala;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setBefbala(String befbala) {
                this.befbala = befbala;
            }

            public String getTranstime() {
                return transtime;
            }

            public String getAftbala() {
                return aftbala;
            }

            public String getAmount() {
                return amount;
            }

            public String getPosition() {
                return position;
            }

            public String getType() {
                return type;
            }

            public String getBefbala() {
                return befbala;
            }
        }
    }
}

