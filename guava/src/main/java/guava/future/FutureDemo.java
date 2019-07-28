package guava.future;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        final ListenableFuture<String> task = service.submit(new RealData("x"));
//        task.addListener(() -> {
//            System.out.println("异步处理成功：");
//            try {
//                System.out.println(task.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        },MoreExecutors.directExecutor());



        /**
         * 更一般的，增加对异常的处理
         */
        Futures.addCallback(task, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("异步处理成功,result="+result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("异步处理失败,e"+t);
            }
        },MoreExecutors.newDirectExecutorService());

        System.out.println("main task done......");
        Thread.sleep(3000);
    }
}
