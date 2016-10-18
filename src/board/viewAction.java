package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URLEncoder;

public class viewAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private boardVO paramClass = new boardVO(); //�Ķ���͸� ������ ��ü
	private boardVO resultClass = new boardVO(); //���� ��� ���� ������ ��ü

	private int currentPage;

	private int no;
	private String password;

	private String fileUploadPath = "f:\\save\\";

	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	// ������
	public viewAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}

	// �󼼺���
	public String execute() throws Exception {

		// �ش� ���� ��ȸ�� +1.
		paramClass.setNo(getNo());
		sqlMapper.update("updateReadHit", paramClass);

		// �ش� ��ȣ�� ���� �����´�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());

		return SUCCESS;
	}

	// ÷�� ���� �ٿ�ε�
	public String download() throws Exception {

		// �ش� ��ȣ�� ���� ������ �����´�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());

		// ���� ��ο� ���ϸ��� file ��ü�� �ִ´�.
		File fileInfo = new File(fileUploadPath + resultClass.getFile_savname());

		// �ٿ�ε� ���� ���� ����.
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="
				+ URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath
				+ resultClass.getFile_savname()));

		return SUCCESS;
	}

	// ��й�ȣ üũ ��
	public String checkForm() throws Exception {

		return SUCCESS;
	}

	// ��й�ȣ üũ �׼�
	public String checkAction() throws Exception {

		// ��й�ȣ �Է°� �Ķ���� ����.
		paramClass.setNo(getNo());
		paramClass.setPassword(getPassword());

		// ���� ���� ��й�ȣ ��������.
		resultClass = (boardVO) sqlMapper.queryForObject("selectPassword",
				paramClass);

		// �Է��� ��й�ȣ�� Ʋ���� ERROR ����.
		if (resultClass == null)
			return ERROR;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}

