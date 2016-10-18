package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class modifyAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private boardVO resultClass; //���� ��� ���� ������ ��ü

	private int currentPage;	//���� ������
	
	private int no;
	private String subject;
	private String name;
	private String password;
	private String content;
	private String old_file;

	private File upload; //���� ��ü
	private String uploadContentType; //������ Ÿ��
	private String uploadFileName; //���� �̸�
	private String fileUploadPath = "f:\\save\\"; //���ε� ���.

	// ������
	public modifyAction() throws IOException {
		
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}

	// �Խñ� ����
	public String execute() throws Exception {
		
		//�Ķ���Ϳ� ����Ʈ ��ü ����.
		paramClass = new boardVO();
		resultClass = new boardVO();

		// ������ �׸� ����.
		paramClass.setNo(getNo());
		paramClass.setSubject(getSubject());
		paramClass.setName(getName());
		paramClass.setPassword(getPassword());
		paramClass.setContent(getContent());

		// �ϴ� �׸� �����Ѵ�.
		sqlMapper.update("updateBoard", paramClass);

		// ������ ������ ���ε� �Ǿ��ٸ� ������ ���ε��ϰ� DB�� file �׸��� ������.
		if (getUpload() != null) {
			
			//���� ������ ����� ���� �̸��� Ȯ���� ����.
			String file_name = "file_" + getNo();
		           String file_ext = getUploadFileName().substring(getUploadFileName().lastIndexOf('.')+1,getUploadFileName().length());
			
			//���� ���� ����
			File deleteFile = new File(fileUploadPath + getOld_file());
			deleteFile.delete();
			
			//�� ���� ���ε�
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(getUpload(), destFile);
			
			//���� ���� �Ķ���� ����.
			paramClass.setFile_orgname(getUploadFileName());
			paramClass.setFile_savname(file_name + "." + file_ext);
			
			//���� ���� ������Ʈ.
			sqlMapper.update("updateFile", paramClass);
		}

		// ������ ������ view �������� �̵�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());

		return SUCCESS;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public boardVO getResultClass() {
		return resultClass;
	}

	public void setResultClass(boardVO resultClass) {
		this.resultClass = resultClass;
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

	public String getOld_file() {
		return old_file;
	}

	public void setOld_file(String old_file) {
		this.old_file = old_file;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}

