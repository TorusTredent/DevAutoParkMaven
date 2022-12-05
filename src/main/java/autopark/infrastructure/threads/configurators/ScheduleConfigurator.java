package autopark.infrastructure.threads.configurators;

import autopark.exception.BadMethodException;
import autopark.infrastructure.config.ProxyConfigurator;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduleConfigurator implements ProxyConfigurator {

    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(Schedule.class) && checkCorrectness(method)) {
                return (T) Enhancer.create(implementation, (MethodInterceptor) this::invoke);
            }
        }
        return object;
    }

    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule schedulesync = method.getAnnotation(Schedule.class);

        if (schedulesync != null) {
            System.out.println(method);
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, schedulesync.timeout(), schedulesync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object obj, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                fThread.setDaemon(true);
                                return fThread;
                            }
                        });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(obj, args);
                                } catch (Throwable throwable) {
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (Exception exception) {
                            executorService.shutdown();
                        }
                        executorService.shutdown();
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.sleep(delta);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private boolean checkCorrectness(Method method) {
        if (method.getReturnType() == void.class && Modifier.isPublic(method.getModifiers())) {
            return true;
        }
        throw new BadMethodException("Schedule-annotated methods must return void and have modify public");
    }
}
