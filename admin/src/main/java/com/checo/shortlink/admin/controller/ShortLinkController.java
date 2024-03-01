package com.checo.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checo.shortlink.admin.common.convention.result.Result;
import com.checo.shortlink.admin.common.convention.result.Results;
import com.checo.shortlink.admin.remote.ShortLinkRemoteService;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkBatchCreateReqDTO;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkBaseInfoRespDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkBatchCreateRespDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.checo.shortlink.admin.toolkit.EasyExcelWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 短链接后管控制层
 */
@RestController
public class ShortLinkController {

    /**
     * TODO 后续重构为 SpringCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 批量创建短链接
     */
    @SneakyThrows
    @PostMapping("/api/short-link/admin/v1/create/batch")
    public void batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam, HttpServletResponse response) {
        Result<ShortLinkBatchCreateRespDTO> shortLinkBatchCreateRespDTOResult = shortLinkRemoteService.batchCreateShortLink(requestParam);
        if (shortLinkBatchCreateRespDTOResult.isSuccess()) {
            List<ShortLinkBaseInfoRespDTO> baseLinkInfos = shortLinkBatchCreateRespDTOResult.getData().getBaseLinkInfos();
            String localDateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String excelFileName = "批量创建短链接-SaaS短链接系统-" + localDateStr;
            EasyExcelWebUtil.write(response, excelFileName, ShortLinkBaseInfoRespDTO.class, baseLinkInfos);
        }
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkRemoteService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkRemoteService.pageShortLink(requestParam);
    }
}
