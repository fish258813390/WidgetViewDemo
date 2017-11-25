package com.neil.demo.handler;

import java.util.UUID;

public class HandlerTest {

    public static void main(String[] args) {

        //轮询器初始化
        Looper.prepare();

        //主线当中
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println(Thread.currentThread().getName() + "  received  " + msg.toString());
            }
        };

        //子线程
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        msg.what = 1;
                        synchronized (UUID.class) {
                            msg.obj = Thread.currentThread().getName() + ",send message:" + UUID.randomUUID().toString();
                        }
                        System.out.println("send" + msg.toString());//发之前要打印出来,如果发了在去打印就会造成收发的顺讯颠倒
                        handler.sendMessage(msg);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ;
            }.start();
        }
        //开启轮询
        Looper.loop();

    }

}
