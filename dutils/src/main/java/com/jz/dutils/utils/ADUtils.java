package com.jz.dutils.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ADUtils {
    public AdDes getAdid(String adinfo, String location, String adtype) {
        boolean isHuawei = false;
        String brand = android.os.Build.BRAND;
        if (brand.equals("Huawei")||brand.equals("HUAWEI")||brand.equals("HONOR")){
            isHuawei = true;
        }

        if (adinfo.equals("")){
            return null;
        }
        int adStatus = 0;
        // 1 只有一个广告类型 2 只有一个广告类型 status可用 3 只有一个广告类型 status不可用 4 只有一个广告类型 status可用  weights 100
        // 5 只有一个广告类型 status可用  weights <100 随机获取展示广告  6 只有一个广告类型 status可用  weights <100 随机获取不展示广告
        // 7 多个广告类型 8   多广告类型 获取到当前切换的广告商    9 多个广告类型status可用 10 多广告类型 获取到当前切换的广告商 weights 100
        // 11 多广告类型 获取到当前切换的广告商 weights <100  随机获取展示广告  12 多广告类型 获取到当前切换的广告商 weights <100  随机获取不展示广告
        //13 多广告类型 获取status不可用  14 当前需要展示的广告商广告id status 不可用 拿一个可用的广告id  weights 100
        // 15 当前需要展示的广告商广告id status 不可用 拿一个可用的广告id weights <100 随机获取展示广告
        // 16 当前需要展示的广告商广告id status 不可用 拿一个可用的广告id weights <100 随机获取不展示广告
        // 17 不是华为手机  google广告禁用
        String adid = "";
        AdDes adDes = new AdDes();
        adDes.setHuawei(isHuawei);
        ADBean adBean = new Gson().fromJson(adinfo,  ADBean.class);
        List< ADBean.DataBean.AdtabBean> posadinfo = new ArrayList<>();

       ADBean.DataBean data = adBean.getData();

        List< ADBean.DataBean.AdtabBean> adtab = data.getAdtab();

        //拿到所有位置所有的匹配广告对象
        for ( ADBean.DataBean.AdtabBean bean : adtab) { //
            if (isHuawei){
                if (bean.getPosition().equals(location) &&bean.getStatus()==1){
                    posadinfo.add(bean);
                }
            } else {
                if (bean.getPosition().equals(location) &&bean.getStatus()==1 &&!bean.getAd().equals("huawei")){
                    posadinfo.add(bean);
                }
            }

        }
        if (posadinfo.size()>0){
            //判断广告类型是一个还是多个

            if (data.getAdtercount() == 1) {
                adStatus=1;
                ADBean.DataBean.AdtabBean bean = posadinfo.get(0);
                if (bean.getStatus() == 1) {
                    adStatus=2;
                    int weights = bean.getWeights();
                    if (weights == 100) {
                        adid = bean.getParameter();
                        adStatus =4;
                    } else {
                        int x = (int) (Math.random() * 100);
                        if (x >= weights) {
                            adid = bean.getParameter();
                            adStatus =5;
                        } else {
                            adStatus =6;
                            adid = "";
                        }
                    }
                } else {
                    adStatus=3;
                }
                adDes.setAdtype(bean.getAd());

            } else {
                adStatus=7;
                for (ADBean.DataBean.AdtabBean bean : posadinfo) {
                    if ( bean.getAd().equals(adtype)) {
                        adStatus=8;
                        if (bean.getStatus()==1){
                            adStatus=9;
                            int weights = bean.getWeights();
                            if (weights == 100) {
                                adid = bean.getParameter();
                                adStatus=10;
                            } else {
                                int x = (int) (Math.random() * 100);
                                if (x >= weights) {
                                    adStatus=11;
                                    adid = bean.getParameter();
                                } else {
                                    adStatus=12;
                                    adid = "";
                                }
                            }
                            adDes.setAdtype(bean.getAd());

                        } else {
                            adStatus=13;
                        }


                    }
                }

                if (adStatus==13){  // 如果当前广告商广告idstatus不可用 切另一个广告
                    for (ADBean.DataBean.AdtabBean bean : posadinfo) {
                        if (bean.getStatus()==1) {
                            int weights = bean.getWeights();
                            if (weights == 100) {
                                adid = bean.getParameter();
                                adStatus=14;
                            } else {
                                int x = (int) (Math.random() * 100);
                                if (x >= weights) {
                                    adStatus=15;
                                    adid = bean.getParameter();
                                } else {
                                    adStatus=16;
                                    adid = "";
                                }
                            }
                            adDes.setAdtype(bean.getAd());

                        }
                    }
                }
            }
        } else {
            adStatus=17;
        }
        Log.d(ADUtils.class.getSimpleName(),"状态==="+adStatus+"==========="+adid);

        adDes.setAdstatus(adStatus);
        try {
            JSONObject jsonObject = new JSONObject(adid);
            adDes.setAdid(jsonObject.getString("param_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return adDes;
    }


    public class AdDes{
        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getAdtype() {
            return adtype;
        }

        public void setAdtype(String adtype) {
            this.adtype = adtype;
        }

        public int getAdstatus() {
            return adstatus;
        }

        public void setAdstatus(int adstatus) {
            this.adstatus = adstatus;
        }

        String adid;
        String adtype;


        public boolean isHuawei() {
            return isHuawei;
        }

        public void setHuawei(boolean huawei) {
            isHuawei = huawei;
        }

        boolean isHuawei;
        int  adstatus;
    }
}
