package com.tianma315.web.scheduler.service.impl;

import com.alibaba.fastjson.JSONArray;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.TaskMapper;
import com.tianma315.core.sys.domain.ScheduleJob;
import com.tianma315.core.sys.domain.TaskDO;
import com.tianma315.core.utils.HttpUtil;
import com.tianma315.web.scheduler.quartz.QuartzManager;
import com.tianma315.core.sys.service.JobService;
import com.tianma315.core.utils.ScheduleJobUtils;
import com.tianma315.web.scheduler.quartz.QuartzManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class JobServiceImpl extends CoreServiceImpl<TaskMapper, TaskDO> implements JobService {
    @Autowired
    QuartzManager quartzManager;

    @Override
    public boolean deleteById(Serializable id) {
        try {
            TaskDO scheduleJob = selectById(id);
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            return retBool(baseMapper.deleteById(id));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteBatchIds(List<? extends Serializable> ids) {
        for (Serializable id : ids) {
            try {
                TaskDO scheduleJob = selectById(id);
                quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }
        return retBool(baseMapper.deleteBatchIds(ids));
    }

    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<TaskDO> jobList = baseMapper.selectList(null);
        for (TaskDO scheduleJob : jobList) {
            if ("1".equals(scheduleJob.getJobStatus())) {
                ScheduleJob job = ScheduleJobUtils.entityToData(scheduleJob);
                quartzManager.addJob(job);
            }

        }
    }

    @Override
    public void changeStatus(Long jobId, String cmd) throws SchedulerException {
        TaskDO scheduleJob = selectById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (STATUS_RUNNING_STOP.equals(cmd)) {
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
        } else {
            if (!STATUS_RUNNING_START.equals(cmd)) {
            } else {
                scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
        }
        updateById(scheduleJob);
    }

    @Override
    public void updateCron(Long jobId) throws SchedulerException {
        TaskDO scheduleJob = selectById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
        }
        updateById(scheduleJob);
    }

    @Override
    public JSONArray getApireallist(String url) {
        JSONArray jsonObject = HttpUtil.httpGets(url);
        return jsonObject;
    }

}
