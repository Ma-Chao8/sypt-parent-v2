package com.tianma315.core.sys.service.impl;

import com.tianma315.core.sys.dao.GeneratorMapper;
import com.tianma315.core.sys.service.GeneratorService;
import com.tianma315.core.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
    @Autowired
    GeneratorMapper generatorMapper;

    @Override
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> list = generatorMapper.list();
        return list;
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            // 查询表信息
            Map<String, String> table = generatorMapper.get(tableName);
            // 查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            // 生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}
