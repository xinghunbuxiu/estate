package com.estate.sdzy.asstes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.estate.exception.BillException;
import com.estate.sdzy.asstes.entity.ROwnerProperty;
import com.estate.sdzy.asstes.entity.RParkingSpace;
import com.estate.sdzy.asstes.mapper.ROwnerPropertyMapper;
import com.estate.sdzy.asstes.mapper.RParkingSpaceMapper;
import com.estate.sdzy.asstes.service.RParkingSpaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.estate.sdzy.common.excel.ExportExcel;
import com.estate.sdzy.common.excel.ImportExcel;
import com.estate.sdzy.system.entity.SUser;
import com.estate.util.BillExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停车位 服务实现类
 * </p>
 *
 * @author mq
 * @since 2020-08-17
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class RParkingSpaceServiceImpl extends ServiceImpl<RParkingSpaceMapper, RParkingSpace> implements RParkingSpaceService {

    private final RParkingSpaceMapper parkingSpaceMapper;
    private final ROwnerPropertyMapper ownerPropertyMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Page<RParkingSpace> listPark(Map<String, String> map, String token) {
        if (StringUtils.isEmpty(map.get("pageNo"))) {
            log.error("参数错误，请输入页码");
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        Integer pageNo = Integer.valueOf(map.get("pageNo"));
        Integer size = StringUtils.isEmpty(map.get("size")) ? 10 : Integer.valueOf(map.get("size"));

        QueryWrapper<RParkingSpace> queryWrapper = new QueryWrapper<>();
        Page<RParkingSpace> page = new Page<>(pageNo,size);
        SUser user = getUserByToken(token);
        log.info("当前角色为->{}", user.getType());
        if (!"超级管理员".equals(user.getType())) {
            queryWrapper.eq("aa.comp_id", user.getCompId());
            // 添加只能查看存在权限的社区条件
            queryWrapper.inSql("aa.comm_id","select  c.comm_id from s_user_comm c where c.user_id= "+user.getId());
        } else {
            // 删除状态
            queryWrapper.eq(StringUtils.isEmpty(map.get("isDelete")), "aa.is_delete", 0);
            queryWrapper.eq(!StringUtils.isEmpty(map.get("isDelete")), "aa.is_delete", map.get("isDelete"));
            queryWrapper.eq(!StringUtils.isEmpty(map.get("compId")),"aa.comp_id", map.get("compId"));
        }
        queryWrapper.eq(!StringUtils.isEmpty(map.get("commId")),"aa.comm_id",map.get("commId"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("no")),"no",map.get("no"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("buildProp")),"building_property",map.get("buildProp"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("useProp")),"use_property",map.get("useProp"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("occupyProp")),"occupy_state",map.get("occupyProp"));

        return parkingSpaceMapper.listPark(page,queryWrapper);
    }

    @Override
    public boolean save(String token, RParkingSpace parkingSpace) {
        SUser user = getUserByToken(token);
        if (null == parkingSpace) {
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        parkingSpace.setCreatedBy(user.getId());
        parkingSpace.setCreatedName(user.getUserName());
        int insert = parkingSpaceMapper.insert(parkingSpace);
        if(insert>0){
            log.info("停车位信息录入成功，录入人{}",user.getUserName());
            return true;
        }
        throw new BillException(BillExceptionEnum.SYSTEM_INSERT_ERROR);
    }


    @Override
    public boolean saveOrUpdate(String token, RParkingSpace parkingSpace) {
        SUser user = getUserByToken(token);
        if (null == parkingSpace) {
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        parkingSpace.setModifiedBy(user.getId());
        parkingSpace.setModifiedName(user.getUserName());

        int update = parkingSpaceMapper.updateById(parkingSpace);
        if(update>0){
            log.info("停车位信息更新成功，更新人{}",user.getUserName());
            return true;
        }
        throw new BillException(BillExceptionEnum.SYSTEM_UPDATE_ERROR);
    }

    @Override
    @Transactional
    public boolean removeById(Long id, String token) {
        SUser user = getUserByToken(token);
        if (null == id) {
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        int delete = parkingSpaceMapper.deleteById(id);
        if(delete > 0){
            log.info("停车位信息删除成功，删除人{}",user.getUserName());
            QueryWrapper<ROwnerProperty> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("property_id",id).eq("property_type","停车位");
            ownerPropertyMapper.delete(queryWrapper);
            return true;
        }
        throw new BillException(BillExceptionEnum.SYSTEM_DELETE_ERROR);
    }

    @Override
    public void writeOut(HttpServletResponse response, String token, Map<String,String> map) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SUser user = getUserByToken(token);
        if(StringUtils.isEmpty(map.get("className"))){
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        Integer size = !StringUtils.isEmpty(map.get("pageTotal")) ? Integer.valueOf(map.get("pageTotal")) : 10;
        QueryWrapper<RParkingSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aa.is_delete",0);
        queryWrapper.eq(!StringUtils.isEmpty(map.get("commId")),"aa.comm_id",map.get("commId"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("no")),"no",map.get("no"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("buildProp")),"building_property",map.get("buildProp"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("useProp")),"use_property",map.get("useProp"));
        queryWrapper.eq(!StringUtils.isEmpty(map.get("occupyProp")),"occupy_state",map.get("occupyProp"));
        Page<RParkingSpace> page = new Page<>(1,size);
        Page<RParkingSpace> rParkingSpacePage = parkingSpaceMapper.listPark(page, queryWrapper);
        List<RParkingSpace> records = rParkingSpacePage.getRecords();
        ExportExcel.writeOut(response,"停车位信息列表",map.get("className"),records,"导出人： "+user.getName());
    }

    @Override
    @Transactional
    public boolean removeByIds(String ids, String token) {
        System.out.println(ids);
        if(StringUtils.isEmpty(ids)){
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        SUser user = getUserByToken(token);
        QueryWrapper<RParkingSpace> queryWrapper = new QueryWrapper<>();
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        queryWrapper.in("id",strings);
        int delete = parkingSpaceMapper.delete(queryWrapper);
        if(delete>0){
            log.info("停车位信息批量删除成功，删除人{}",user.getUserName());
            QueryWrapper<ROwnerProperty> qw = new QueryWrapper<>();
            qw.in("property_id",strings).eq("property_type","停车位");
            ownerPropertyMapper.delete(qw);
            return true;
        }
        throw new BillException(BillExceptionEnum.SYSTEM_DELETE_ERROR);
    }

    @Override
    public boolean insertBatch(List<T> list) {
        return false;
    }

    @Override
    @Transactional
    public boolean fileUpload(MultipartFile file, String className) throws IOException, ClassNotFoundException {
        if(StringUtils.isEmpty(className)){
            throw new BillException(BillExceptionEnum.PARAMS_MISS_ERROR);
        }
        List<Object> fileData = ImportExcel.getFileData(file, className);
        fileData.forEach(x->{
            RParkingSpace s = (RParkingSpace)x;
            QueryWrapper<RParkingSpace> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("no",s.getNo()).eq("comm_area_id",s.getCommAreaId());
            RParkingSpace rParkingSpace = parkingSpaceMapper.selectOne(queryWrapper);
            if(rParkingSpace == null){
                int insert = parkingSpaceMapper.insert(s);
                if(!(insert > 0)){
                   throw new BillException(BillExceptionEnum.SYSTEM_INSERT_ERROR);
                }
            }else{
                log.error("导入数据重复，请查证并修改后在重新导入！重复数据编号：<{}>",s.getNo());
                throw new BillException(BillExceptionEnum.DATA_EXIST_ERROR);
            }

        });
        return true;
    }

    private SUser getUserByToken(String token) {
        Object o = redisTemplate.opsForValue().get(token);
        if (null == o) {
            log.error("登录失效，请重新登录。");
            throw new BillException(BillExceptionEnum.LOGIN_TIME_OUT);
        }
        return (SUser) o;
    }
}
