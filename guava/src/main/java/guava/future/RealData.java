package guava.future;

import java.util.concurrent.Callable;

/**
 * 真实数据，即最终需要使用的数据模型
 */
public class RealData implements Callable<String>{//jdk内置的future模式
    protected String result;

    public RealData(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(result);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
