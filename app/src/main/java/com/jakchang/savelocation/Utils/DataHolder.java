package com.jakchang.savelocation.Utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataHolder {

        public DataHolder(){}
        public static DataHolder getInstance(){

            return LazyHolder.INSTANCE;
        }
        private static class LazyHolder{
            private static final DataHolder INSTANCE = new DataHolder();
        }


        private static Map<String, Object> mDataHolder = new ConcurrentHashMap<>();

        public static void putDataHolder(String key , Object data){
            //중복되지 않는 홀더 아이디를 생성해서 요청자에게 돌려준다.
            mDataHolder.put(key, data);
            //return dataHolderId;
        }


        public static Object getDataHolder(String key){
        Object obj = mDataHolder.get(key);
        return obj;
        }

        public static Object popDataHolder(String key){
            Object obj = mDataHolder.get(key);
            //pop된 데이터는 홀더를 제거
            mDataHolder.remove(key);
            return obj;
        }




}
