package com.checo.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checo.shortlink.admin.common.convention.result.Result;
import com.checo.shortlink.admin.common.convention.result.Results;
import com.checo.shortlink.admin.remote.ShortLinkRemoteService;
import com.checo.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.checo.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站管理控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * TODO 后续重构为 SpringCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 保存回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }
}