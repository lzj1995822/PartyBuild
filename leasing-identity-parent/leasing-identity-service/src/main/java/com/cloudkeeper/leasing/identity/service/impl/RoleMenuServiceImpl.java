package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.Principal;
import com.cloudkeeper.leasing.identity.domain.RoleMenu;
import com.cloudkeeper.leasing.identity.domain.SysRoutes;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.repository.RoleMenuRepository;
import com.cloudkeeper.leasing.identity.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色菜单 service
 * @author jerry
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements RoleMenuService {

    /** 角色菜单 repository */
    private final RoleMenuRepository roleMenuRepository;

    private final SysUserServiceImpl sysUserService;

    @Override
    protected BaseRepository<RoleMenu> getBaseRepository() {
        return roleMenuRepository;
    }

    @Nonnull
    @Override
    public List<RoleMenu> findAllByRoleId(@Nonnull String roleId) {
        return roleMenuRepository.findAllByRoleIdOrderByCreatedAtAsc(roleId);
    }

    @Override
    public void deleteAllByRoleId(@Nonnull String roleId) {
        roleMenuRepository.deleteAllByRoleId(roleId);
    }

    @Nonnull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RoleMenu> save(@Nonnull String roleId, @Nonnull List<String> menuCodeList) {
        roleMenuRepository.deleteAllByRoleId(roleId);
        return menuCodeList.stream().map(menuCode -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setSysRouteId(menuCode);
            return roleMenuRepository.save(roleMenu);
        }).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public List<SysRoutes> findAllMenuCodeByPrincipalId(@Nonnull String principalId) {
        Optional<SysUser> optionalById = sysUserService.findOptionalById(principalId);
        if (!optionalById.isPresent()) {
            return new ArrayList<>();
        }
        List<RoleMenu> roleMenus = roleMenuRepository.findAllByRoleIdOrderBySysRoutesCreatedAtAsc(optionalById.get().getRoleID());
        Map<String, SysRoutes> firstMenus = new LinkedHashMap<>();
        ArrayList<SysRoutes> childMenus = new ArrayList<>();
        SysRoutes sysRoutes;
        for (RoleMenu item : roleMenus) {
            sysRoutes = item.getSysRoutes();
            if (sysRoutes.getParentId() == null) {
                sysRoutes.setChildren(new ArrayList<>());
                firstMenus.put(sysRoutes.getId(), sysRoutes);
            } else {
                childMenus.add(sysRoutes);
            }
        }
        List<SysRoutes> children;
        for (SysRoutes subItem : childMenus) {
            children = firstMenus.get(subItem.getParentId()).getChildren();
            children.add(subItem);
        }
        return new ArrayList(firstMenus.values());
    }
}
