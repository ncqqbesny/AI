package com.soft.mapp.basecenter.impl.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soft.mapp.basecenter.dao.ITaskDao;
import com.soft.mapp.basecenter.domain.task.*;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.ITaskService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.HttpTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    ITaskDao taskDao;


    @Override
    public List<OrdersPo> findByUserid(Integer userId) {
        return null;
    }

    /**
     * 下发任务
     * @param task
     * @return
     */
    @Override
    public int addInfo(OrdersVo task) {
        //下发任务参数
        ReqOrdersBean reqOrder=getreqOrderParam(task);
        //开始调用设备中间件服务
        String url="http://127.0.0.1:8921/agv/geekServices/sendOrders";
        String jsonParam= JSON.toJSONString(reqOrder);
        String result= HttpTools.doJsonPost(url,jsonParam,null);
        JSONObject  resultJson=JSONObject.parseObject(result);
        int count=0;
        if(null!=resultJson && null!=resultJson.get("code") && resultJson.get("code").equals("0")){
            count=1;
        }
        return count;
    }

    private ReqOrdersBean  getreqOrderParam(OrdersVo task){
        ReqOrdersBean orderParam= new ReqOrdersBean();
        String reqCode="";
        UUID uuid = UUID.randomUUID();
        reqCode=uuid.toString().replace("-","");
        orderParam.setReqCode(reqCode);
        List<OrdersBean> orders=new ArrayList<>();
        OrdersBean order=getOrderBean(task);
        orders.add(order);
        orderParam.setOrders(orders);
        return orderParam;
    }

    /**
     * 获得下发任务的明细
     * @param task
     * @return
     */
    private OrdersBean  getOrderBean(OrdersVo task){
        OrdersBean order=new OrdersBean();
        String jobId="1";
        if(StringUtils.isEmpty(task.getTaskJobId())){
            Long jobSeqId=taskDao.getOrderId();
            jobId= ConvertUtils.toString(jobSeqId);
        }else{
            jobId=task.getTaskJobId();
        }
        String orderId="1";
        if(StringUtils.isNotEmpty(task.getOrderId())){
            orderId=ConvertUtils.toString(ConvertUtils.toInt(task.getOrderId())+1);
        }
        order.setJobId(jobId);
        order.setOrderId(orderId);
        order.setOrderType(task.getOrderType());
        order.setFrom(task.getFrom());
        order.setTo(task.getTo());
        order.setIsDrop(task.getIsDrop());
        order.setIsLastOrder(task.getIsLastOrder());
        order.setPriority(task.getPriority());
        return  order;
    }

    @Override
    public int updateByExampleSelective(OrdersVo task) {
        return 0;
    }

    @Override
    public int deleteByExample(List<Integer> ids) {
        return 0;
    }

    @Override
    public CommonPage<OrdersVo> getPageBySelective(OrdersVo order, Integer pageSize, Integer pageNum) {
        TaskQuery taskQuery = new TaskQuery();
        TaskQuery.Criteria criteria = taskQuery.createCriteria();
        if (StringUtils.isNotEmpty(order.getReqCode())) {
            criteria.andReqCodeLike("%" + order.getReqCode() + "%");
        }
        if (StringUtils.isNotEmpty(order.getTaskJobId())) {
            criteria.andJobIdLike("%" + order.getTaskJobId() + "%");
        }
        if (StringUtils.isNotEmpty(order.getOrderId())) {
            criteria.andOrderIdLike("%" + order.getOrderId() + "%");
        }
        if (StringUtils.isNotEmpty(order.getFrom())) {
            criteria.andFormLike("%" + order.getFrom() + "%");
        }
        if (StringUtils.isNotEmpty(order.getTo())) {
            criteria.andToLike("%" + order.getTo() + "%");
        }
        if (StringUtils.isNotEmpty(order.getOrderType())) {
            criteria.andOrderTypeEqualTo(order.getOrderType());
        }
        criteria.andIsParent();
        taskQuery.setOrderByClause("create_time desc,update_time desc");
        //增加分页
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            taskQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        List<OrdersVo> taskVos = new ArrayList<OrdersVo>();
        List<?> listAndTotal = taskDao.getPageInfo(taskQuery);
        List<OrdersPo> taskDTOs = (List<OrdersPo>) listAndTotal.get(0);
        Long total = ((List<Long>) listAndTotal.get(1)).get(0);
        CommonPage<OrdersVo> page = new CommonPage<OrdersVo>();
        if (CollectionUtils.isEmpty(taskDTOs)) {
            return page;
        }
        for(OrdersPo ordersPo:taskDTOs){
            OrdersVo ordervo=new OrdersVo();
            BeanUtils.copyProperties(ordersPo,ordervo);
            List<OrdersPo> children= getTaskChildList(ordersPo.getId());
            ordervo.setChildren(children);
            taskVos.add(ordervo);
        }
        //List<UserVo>  userVos= JSONArray.parseArray(JSON.toJSONString(userDTOs), UserVo.class);
        page.setList(taskVos);
        page.setTotal(total);
        return page;
    }

    private  List<OrdersPo> getTaskChildList(Integer id){
        TaskQuery taskQuery = new TaskQuery();
        TaskQuery.Criteria criteria = taskQuery.createCriteria();
        criteria.andIsChildren();
        criteria.andParentIdEqualTo(id);
        List<?> listAndTotal=taskDao.getPageInfo(taskQuery);
        List<OrdersPo> list=(List<OrdersPo>)listAndTotal.get(0);
        return  list;
    }
}
