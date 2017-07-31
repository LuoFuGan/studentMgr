package com.etc.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	
	//���ļ��ϴ�����·��·��
	private static final String UPLOAD_DIRECTORY = "upload\\test";
	//�ϴ�����
	//�ϴ�ʱ����ʱ�ռ�Ĵ�С
	private static final int MERORY_THRESHOLD = 1024*1024*3;//3MB
	//�ļ��ϴ�ʱ�Ĵ�С
	private static final int MAX_FILE_SIZE = 1024*1024*40;//40MB
	//�ϴ�����Ĵ�С
	private static final int MAX_REQUEST_SIZE = 1024*1024*50;//50MB

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//�ж��Ƿ����ϴ��ļ��Ĳ���
		if(!ServletFileUpload.isMultipartContent(request)){
			request.setAttribute("message", "����������ϴ��ļ��Ĳ���");
			request.getRequestDispatcher("upload.jsp").forward(request, response);
		}else{
			//�����ϴ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�����ڴ��ٽ�ֵ-��������󽫲�����ʱ�ļ����洢����ʱĿ¼��
			factory.setSizeThreshold(MERORY_THRESHOLD);
			//������ʱ�洢�ļ���Ŀ¼
			System.out.println(System.getProperty("java.io.tmpdir"));
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//��������ļ��ϴ�ֵ
			upload.setFileSizeMax(MAX_FILE_SIZE);
			//�����������ֵ(���������ݺ��ļ�)
			upload.setSizeMax(MAX_REQUEST_SIZE);
			
			upload.setHeaderEncoding("UTF-8");
			
			//������ʱ·�����洢�ϴ����ļ�
			String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
			
			File uploadDir = new File(uploadPath);
			//���Ŀ¼�������򴴽�
			if(!uploadDir.exists()){
				uploadDir.mkdir();
			}
			
			try {
				List<FileItem> formItems = upload.parseRequest(request);
				if(formItems != null && formItems.size() > 0){
					for(FileItem item : formItems){
						if(!item.isFormField()){
							String fileName = new File(item.getName()).getName();
							String filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							System.out.println(filePath);
							item.write(storeFile);
							request.setAttribute("message","�ļ��ϴ��ɹ���");
						}else{
							System.out.println(item.getFieldName());
							System.out.println(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("upload.jsp").forward(request, response);
	}

}
