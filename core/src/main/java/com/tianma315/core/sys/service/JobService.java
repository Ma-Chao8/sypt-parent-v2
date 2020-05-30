package com.tianma315.core.sys.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.sys.domain.TaskDO;
import org.quartz.SchedulerException;


/**
 * <pre>
 * 定时任务
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
public interface JobService extends CoreService<TaskDO> {
    String STATUS_RUNNING_STOP = "stop";    // 停止计划任务
    String STATUS_RUNNING_START = "start";    // 开启计划任务


    void initSchedule() throws SchedulerException;

    void changeStatus(Long jobId, String cmd) throws SchedulerException;

    void updateCron(Long jobId) throws SchedulerException;

    JSONArray getApireallist(String url);
}
