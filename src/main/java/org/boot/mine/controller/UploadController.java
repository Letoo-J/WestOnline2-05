package org.boot.mine.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.User;
import org.boot.mine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import antlr.collections.impl.IntRange;
import net.coobird.thumbnailator.Thumbnails;


//头像上传接口
@Controller

public class UploadController{
	@Autowired
	UserServiceImpl _userService;
		
	@PostMapping("/post/upload")
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
		 int size=200;
         long  startTime=System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
        	
        	
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext()){
            	
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null){
                	BufferedImage bi = ImageIO.read(file.getInputStream());  //获取图像输入流
        			int w=bi.getWidth();   //得到宽度
        			int h=bi.getHeight();  //得到高度
        			int max = (int) Math.max(w, h);  //对比谁更大
        	        int tow = w;
        	        int toh = h;
        	        if (max > size) {
        	            if (w > h) {
        	                tow = size;
        	                toh = h * size / w;
        	            } else {
        	                tow = w * size / h;
        	                toh = size;
        	            }
        	        }
                	File path = new File(ResourceUtils.getURL("classpath:").getPath());
                	if(!path.exists()) path = new File("");
              
                	String pathhead=path.getAbsolutePath();
                	//path1:文件最终存储位置
                    String path1=pathhead+"/XXXX/"+file.getOriginalFilename();
                    System.out.println("存储位置"+path1);
                    Thumbnails.of(file.getInputStream())    
	        		.size(tow, toh)             //缩放 设置大小
	        		.outputFormat("jpg")		//指定输出类型
	        		.toFile(path1);             //上传
                    //file.transferTo(new File(path1));
                }
                //文件名传入数据库
                User u =(User) request.getSession().getAttribute("user");
                u.setPicName(file.getOriginalFilename());
                _userService.updateUser(u);
                System.out.println("文件上传成功！");
            }
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "home/person"; 
    }
}