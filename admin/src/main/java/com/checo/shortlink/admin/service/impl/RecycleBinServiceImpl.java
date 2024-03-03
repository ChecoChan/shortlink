package com.checo.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.checo.shortlink.admin.common.biz.user.UserContext;
import com.checo.shortlink.admin.common.convention.exception.ServiceException;
import com.checo.shortlink.admin.common.convention.result.Result;
import com.checo.shortlink.admin.dao.entity.GroupDO;
import com.checo.shortlink.admin.dao.mapper.GroupMapper;
import com.checo.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.checo.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.checo.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.checo.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 回收站管理接口实现层
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {

    private final GroupMapper groupMapper;
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    @Override
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOS = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOS)) {
            throw new ServiceException("用户无分组信息");
        }
        requestParam.setGidList(groupDOS.stream().map(GroupDO::getGid).toList());
        return shortLinkActualRemoteService.pageRecycleBinShortLink(requestParam);
    }
}
