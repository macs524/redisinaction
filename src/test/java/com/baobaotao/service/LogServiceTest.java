package com.baobaotao.service;

import com.baobaotao.service.impl.CommonsLoggingLogService;
import com.baobaotao.service.impl.Log4jService;
import com.baobaotao.service.impl.LogSlf4jService;
import org.junit.Test;

/**
 * 测试各种日志服务
 * Created by machangsheng on 16/5/6.
 */
public class LogServiceTest {
    /**
     * logServcie
     */
    private LogService logService;

    @Test
    public void testLog4j(){
        logService = new Log4jService();

        logService.debug("Debug Info");
    }

    @Test
    public void testSlf4j(){
        logService = new LogSlf4jService();
        logService.debug("Another Debut Info");
    }

    @Test
    public void testCommonsLog(){
        logService = new CommonsLoggingLogService();
        logService.debug("Commons Logging");
    }
}
