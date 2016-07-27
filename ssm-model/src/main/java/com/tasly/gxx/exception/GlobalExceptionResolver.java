package com.tasly.gxx.exception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.tasly.gxx.dto.BaseResult;

/**
 * ������Ϣͳһ����
 * ��δ����Ĵ�����Ϣ��һ��ͳһ����
 * @author gaoxiexin
 *
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		LOG.error("�û� " + WebUtils.getSessionAttribute(request, "currentUser")+ " ����" + request.getRequestURI() + " ��������, ������Ϣ:" + ex.getMessage());
		//������2��ѡ��
		//��ת�����ƻ��Ĵ���ҳ��
	    /*ModelAndView error = new ModelAndView("error");
		error.addObject("exMsg", ex.getMessage());
		error.addObject("exType", ex.getClass().getSimpleName().replace("\"", "'"));*/
		//����json��ʽ�Ĵ�����Ϣ
		try {
			PrintWriter writer = response.getWriter();
			BaseResult<String> result=new BaseResult(false, ex.getMessage());
			writer.write(JSON.toJSONString(result));
			writer.flush();
		} catch (Exception e) {
		}
		return null;
	}
	

}
