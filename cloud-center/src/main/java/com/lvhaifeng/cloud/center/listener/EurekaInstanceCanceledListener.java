package com.lvhaifeng.cloud.center.listener;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Eureka 服务检测时间
 * @author haifeng.lv
 * @date 2019-07-27 22:54
 */
@Configuration
public class EurekaInstanceCanceledListener implements ApplicationListener {
    private static Logger log = LoggerFactory.getLogger(EurekaInstanceCanceledListener.class);
    private ConcurrentHashMap<String, LostInstance> lostInstanceMap = new ConcurrentHashMap<String, LostInstance>();
    private Integer[] defaultNotifyInterval = {0, 60, 120, 240, 480};

    @Value("${eureka.instance.hostname}")
    private String EurekaHostname;

    public String getEurekaHostname() {
        return EurekaHostname;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 服务挂掉自动通知
        if (applicationEvent instanceof EurekaInstanceCanceledEvent) {
            // 应该设置时长，多少秒后服务没有启动如何
            EurekaInstanceCanceledEvent event = (EurekaInstanceCanceledEvent) applicationEvent;
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
            Applications applications = registry.getApplications();
            applications.getRegisteredApplications().forEach((registeredApplication) -> {
                registeredApplication.getInstances().forEach((instance) -> {
                    if (instance.getInstanceId().equals(event.getServerId())) {
                        String id = instance.getInstanceId();
                        lostInstanceMap.remove(id);
                        lostInstanceMap.put(id, new LostInstance(instance));
                        registry.getApplication(instance.getAppName()).removeInstance(instance);
                    }
                });
            });
        }
        if (applicationEvent instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent event = (EurekaInstanceRegisteredEvent) applicationEvent;
            log.info("服务：{}/{} 注册成功，IP为：{}:{}，服务hostname：{},注册时间为：{}", new Object[]{event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId(), event.getInstanceInfo().getIPAddr(), event.getInstanceInfo().getPort(), getEurekaHostname(), LocalDateTime.now().toString()});
            lostInstanceMap.remove(event.getInstanceInfo().getInstanceId());
        }
    }

    /**
     * 20分钟检测一次服务状态
     * @author haifeng.lv
     * @date 2019-07-27 22:54
     */
    @Scheduled(cron = "0 0/20 * * * ?")
    private void notifyLostInstance() {
        lostInstanceMap.entrySet().forEach((lostInstanceMap) -> {
            LostInstance lostInstance = lostInstanceMap.getValue();
            if (lostInstance.getLostTime().plusSeconds(defaultNotifyInterval[lostInstance.getCurrentInterval()]).isBefore(LocalDateTime.now())) {
                log.info("服务：{}/{}已失效，IP为：{}:{}，失效时间为：{}，请马上重启服务！", new Object[]{lostInstance.getAppName(), lostInstance.getInstanceId(), lostInstance.getIPAddr(), lostInstance.getPort(), lostInstance.getLostTime().toString()});
                log.info("服务hostname：{}，失效时间为：{},失败次数{}！", new Object[]{getEurekaHostname(), lostInstance.getLostTime().toString(), lostInstance.getCurrentInterval()});
            }
        });
    }

    class LostInstance extends InstanceInfo {
        protected int currentInterval = 0;
        protected LocalDateTime lostTime;

        public LostInstance(InstanceInfo ii) {
            super(ii);
            this.lostTime = LocalDateTime.now();
        }

        public LocalDateTime getLostTime() {
            return lostTime;
        }
        public int getCurrentInterval() {
            return currentInterval++ % 4;
        }
    }
}
