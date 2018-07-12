package io.choerodon.wiki.api.controller.v1;

import java.util.Optional;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.choerodon.wiki.api.dto.WikiGroupDTO;
import io.choerodon.wiki.app.service.WikiGroupService;
import io.choerodon.wiki.infra.common.GetUserNameUtil;

/**
 * Created by Ernst on 2018/7/6.
 */
@RestController
@RequestMapping(value = "/v1/organizations/{organization_id}/groups")
public class WikiGroupController {

    private WikiGroupService wikiGroupService;

    public WikiGroupController(WikiGroupService wikiGroupService) {
        this.wikiGroupService = wikiGroupService;
    }

    /**
     * 创建wiki组
     *
     * @param wikiGroupDTO 用户信息
     * @return responseEntity
     */
    @Permission(level = ResourceLevel.ORGANIZATION, roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR})
    @ApiOperation(value = "创建wiki组")
    @PostMapping
    public ResponseEntity<Boolean> create(
            @ApiParam(value = "组信息", required = true)
            @RequestBody @Valid WikiGroupDTO wikiGroupDTO) {

        return Optional.ofNullable(wikiGroupService.create(wikiGroupDTO, GetUserNameUtil.getUsername()))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.group.create"));
    }

}
