package com.rnkrsoft.opensource.dms.services;


/**
 * Created by rnkrsoft.com on 2019/7/2.
 */
public interface GenerateFileService {
    /**
     * 生成导出文件
     * @param fileName 文件名
     * @param callback 分段回调接口
     */
    void generate(String fileName, int pageNum, int pageSize, SegmentalCallback callback);
}
