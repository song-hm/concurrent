package com.shm.parallel.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 并行流水线
 * 场景：计算（B+C）*B/2
 * 流水线拆分：
 * P1:A=B+C
 * P2:D=A*B
 * P3:D=D/2
 */
public class PPipeline {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 1; i <= 1000; i++){
            for (int j = 1; j <= 1000; j++){
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "(("+i+"+"+j+")*"+i+")/2";
                Plus.bq.add(msg);
            }
        }

    }

}
