package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class writeAction extends ActionSupport {

	public static Reader reader; //���� ��Ʈ���� ���� reader.
	public static SqlMapClient sqlMapper; //SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü.

	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private boardVO resultClass; //���� ��� ���� ������ ��ü

	private int currentPage; //���� ������

	private int no;
	private String subject;
	private String name;
	private String password;
	private String content;
	private String file_orgName; //���ε� ������ ���� �̸�
	private String file_savName; //������ ������ ���ε� ������ �̸�. ���� ��ȣ�� �����Ѵ�.
	Calendar today = Calendar.getInstance(); //���� ��¥ ���ϱ�.

	private File upload; //���� ��ü
	private String uploadContentType; //������ Ÿ��
	private String uploadFileName; //���� �̸�
	private String fileUploadPath = "f:\\save\\"; //���ε� ���.

	// ������
	public writeAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}

	public String form() throws Exception {
		//��� ��.
		return SUCCESS;
	}

	// �Խ��� WRITE �׼�
	public String execute() throws Exception {

		//�Ķ���Ϳ� ����Ʈ ��ü ����.
		paramClass = new boardVO();
		resultClass = new boardVO();

		// ����� �׸� ����.
		paramClass.setSubject(getSubject());
		paramClass.setName(getName());
		paramClass.setPassword(getPassword());
		paramClass.setContent(getContent());
		paramClass.setRegdate(today.getTime());

		// ��� ���� ����.
		sqlMapper.insert("insertBoard", paramClass);

		// ÷�������� �����ߴٸ� ������ ���ε��Ѵ�.
		if (getUpload() != null) {

			//����� �� ��ȣ ��������.
			resultClass = (boardVO) sqlMapper.queryForObject("selectLastNo");

			//���� ������ ����� ���� �̸��� Ȯ���� ����.
			String file_name = "file_" + resultClass.getNo();
			String file_ext = getUploadFileName().substring(
					getUploadFileName().lastIndexOf('.') + 1,
					getUploadFileName().length());

			//������ ���� ����.
			File destFile = new File(fileUploadPath + file_name + "."
					+ file_ext);
			FileUtils.copyFile(getUpload(), destFile);

			//���� ���� �Ķ���� ����.
			paramClass.setNo(resultClass.getNo());
			paramClass.setFile_orgname(getUploadFileName());		//���� ���� �̸�
			paramClass.setFile_savname(file_name + "." + file_ext);	//������ ������ ���� �̸�

			//���� ���� ������Ʈ.
			sqlMapper.update("updateFile", paramClass);
		}

		return SUCCESS;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getFile_orgName() {
		return file_orgName;
	}

	public void setFile_orgName(String file_orgName) {
		this.file_orgName = file_orgName;
	}

	public String getFile_savName() {
		return file_savName;
	}

	public void setFile_savName(String file_savName) {
		this.file_savName = file_savName;
	}

	public boardVO getResultClass() {
		return resultClass;
	}

	public void setResultClass(boardVO resultClass) {
		this.resultClass = resultClass;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
