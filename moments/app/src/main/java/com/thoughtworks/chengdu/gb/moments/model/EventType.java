package com.thoughtworks.chengdu.gb.moments.model;

import cn.droidlover.xdroidmvp.event.IBus;

/**
 * Created by GB on 2017/12/6.
 */

public class EventType{
    public static class ZXingCode implements IBus.IEvent{

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int getTag() {
            return 0;
        }
    }
}
