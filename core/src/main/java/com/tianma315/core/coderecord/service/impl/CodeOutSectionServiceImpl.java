package com.tianma315.core.coderecord.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.coderecord.dao.CodeOutSectionMapper;
import com.tianma315.core.coderecord.domain.pojo.CodeOutSection;
import com.tianma315.core.coderecord.service.CodeOutSectionService;
import com.tianma315.core.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/9/24.
 */
@Service
public class CodeOutSectionServiceImpl implements CodeOutSectionService {

    @Autowired
    private CodeOutSectionMapper codeOutSectionMapper;

    @Override
    public List<CodeOutSection> getSectionByCompany(long company_id) {

        Wrapper<CodeOutSection> wrapper = new EntityWrapper<>();
        wrapper.eq("company_id", company_id);
        List<CodeOutSection> codeOutSections = codeOutSectionMapper.selectList(wrapper);


        return codeOutSections;
    }

    @Override
    public Page getPage(int pageNumber, int pageSize) {
        Page page = new Page(pageNumber, pageSize);
        List<CodeOutSection> list = codeOutSectionMapper.selectPage(page, null);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean save(CodeOutSection section) {
        //查询当前区间的序列号是否被使用
        Wrapper<CodeOutSection> wrapper = new EntityWrapper()
                .le("serial_start", section.getSerialStart())
                .ge("serial_end", section.getSerialEnd())
                .orNew()
                .gt("serial_start", section.getSerialStart())
                .lt("serial_start", section.getSerialEnd())
                .orNew()
                .gt("serial_end", section.getSerialStart())
                .lt("serial_end", section.getSerialEnd())
                .orNew()
                .ge("serial_start", section.getSerialStart())
                .le("serial_end", section.getSerialEnd());

        if (codeOutSectionMapper.selectCount(wrapper) > 0) {
            throw new MessageException("当前序列号已被使用");
        }

        if (codeOutSectionMapper.insert(section) != 1) {
            throw new MessageException("出码记录保存失败");
        }
        return true;
    }

}
