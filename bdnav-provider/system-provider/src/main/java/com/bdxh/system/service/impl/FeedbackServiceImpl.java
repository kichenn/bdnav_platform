package com.bdxh.system.service.impl;

import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.support.BaseService;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Feedback;
import com.bdxh.system.entity.FeedbackAttach;
import com.bdxh.system.persistence.FeedbackAttachMapper;
import com.bdxh.system.persistence.FeedbackMapper;
import com.bdxh.system.service.FeedbackService;
import com.bdxh.system.vo.FeedbackAttachVo;
import com.bdxh.system.vo.FeedbackVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: 用户反馈业务层实现
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Service
@Slf4j
public class FeedbackServiceImpl extends BaseService<Feedback> implements FeedbackService {

	@Autowired
	private FeedbackMapper feedbackMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private FeedbackAttachMapper feedbackAttachMapper;


	/*
	 *查询总条数
	 */
	@Override
	public Integer getFeedbackAllCount(){
		return feedbackMapper.getFeedbackAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelFeedbackInIds(List<Long> ids){
		return feedbackMapper.delFeedbackInIds(ids) > 0;
	}

	/**
	 * 添加意见反馈
	 *
	 * @param addFeedbackDto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addFeedback(AddFeedbackDto addFeedbackDto) {
		//数据拷贝
		Feedback feedback = new Feedback();
		BeanUtils.copyProperties(addFeedbackDto, feedback);
		Long feedbackId = snowflakeIdWorker.nextId();
		feedback.setId(feedbackId);
		Boolean result = feedbackMapper.insertSelective(feedback)>0;
		//添加附件图片
		List<AddFeedbackAttachDto> images = addFeedbackDto.getImage();
		if(CollectionUtils.isNotEmpty(images)){
			images.forEach(item->{
				FeedbackAttach feedbackAttach = new FeedbackAttach();
				BeanUtils.copyProperties(item, feedbackAttach);
				feedbackAttach.setFeedbackId(feedbackId);
				feedbackAttachMapper.insertSelective(feedbackAttach);
			});
		}
		return result;
	}

	/**
	 * 根据反馈信息的主键id删除用户反馈信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delFeedback(Long id) {
		//删除附件信息
		FeedbackAttach feedbackAttach = new FeedbackAttach();
		feedbackAttach.setFeedbackId(id);
		List<FeedbackAttach> feedbackAttachs = feedbackAttachMapper.select(feedbackAttach);
		if(CollectionUtils.isNotEmpty(feedbackAttachs)){
			feedbackAttachs.forEach(item->{
				feedbackAttachMapper.deleteByPrimaryKey(item.getId());
			//删除云端图片
			//FileOperationUtils.deleteFile(item.getImgName(),null);
			});
		}
		//删除用户反馈信息
		return feedbackMapper.deleteByPrimaryKey(id)>0;
	}


	/**
	 * 修改用户反馈信息
	 *
	 * @param modifyFeedbackDto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean modifyFeedback(ModifyFeedbackDto modifyFeedbackDto) {
		//数据拷贝
		Feedback feedback = new Feedback();
		BeanUtils.copyProperties(modifyFeedbackDto, feedback);
		Boolean result = feedbackMapper.updateByPrimaryKeySelective(feedback) > 0;
		//删除附件信息
		FeedbackAttach feedbackAttach = new FeedbackAttach();
		feedbackAttach.setFeedbackId(modifyFeedbackDto.getId());
		List<FeedbackAttach> feedbackAttachs = feedbackAttachMapper.select(feedbackAttach);
		if(CollectionUtils.isNotEmpty(feedbackAttachs)){
			feedbackAttachs.forEach(item->{
				feedbackAttachMapper.deleteByPrimaryKey(item.getId());
				//删除云端图片
				//FileOperationUtils.deleteFile(item.getImgName(),null);
			});
		}
		//添加新的附件
		if(CollectionUtils.isNotEmpty(modifyFeedbackDto.getImage())){
			modifyFeedbackDto.getImage().forEach(item->{
				FeedbackAttach attach = new FeedbackAttach();
				BeanUtils.copyProperties(item, attach);
				attach.setFeedbackId(modifyFeedbackDto.getId());
				feedbackAttachMapper.insertSelective(attach);
			});
		}
		return result;
	}

	/**
	 * 根据条件分页查询用户反馈信息
	 *
	 * @param feedbackQueryDto
	 * @return
	 */
	@Override
	public PageInfo<FeedbackVo> findFeedbackByCondition(FeedbackQueryDto feedbackQueryDto) {
		PageHelper.startPage(feedbackQueryDto.getPageNum(),feedbackQueryDto.getPageSize());
		//数据拷贝
		Feedback feedback = new Feedback();
		BeanUtils.copyProperties(feedbackQueryDto, feedback);
		List<Feedback> feedbackList = feedbackMapper.findFeedbackByCondition(feedback);
		//查询附件信息
		List<FeedbackVo> feedbackVos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(feedbackList)){
			feedbackList.stream().forEach(item->{
				FeedbackVo feedbackVo = new FeedbackVo();
				BeanUtils.copyProperties(item, feedbackVo);
				List<FeedbackAttach> feedbackAttaches = feedbackAttachMapper.queryFeedbackAttackByFeedId(item.getId());
                List<FeedbackAttachVo> feedbackAttachVos = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(feedbackAttaches)){
					feedbackAttaches.stream().forEach(attach->{
						FeedbackAttachVo attachVo = new FeedbackAttachVo();
						BeanUtils.copyProperties(attach, attachVo);
                        feedbackAttachVos.add(attachVo);
					});
				}
                feedbackVo.setImage(feedbackAttachVos);
				feedbackVos.add(feedbackVo);
			});
		}
		PageInfo<FeedbackVo> pageInfo = new PageInfo<>(feedbackVos);
		pageInfo.setTotal(feedbackVos.size());
		return pageInfo;
	}
}
