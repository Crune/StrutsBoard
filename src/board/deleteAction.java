package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.File;
import java.io.Reader;
import java.io.IOException;

public class deleteAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private boardVO resultClass; //���� ��� ���� ������ ��ü

	private int currentPage;	//���� ������
	
	private String fileUploadPath = "f:\\save\\";
	
	private int no;
	
	
	// ������
	public deleteAction() throws IOException {
		
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}

	// �Խñ� �� ����
	public String execute() throws Exception {
		
		//�Ķ���Ϳ� ����Ʈ ��ü ����.
		paramClass = new boardVO();
		resultClass = new boardVO();
		
		// �ش� ��ȣ�� ���� �����´�.
		resultClass = (boardVO) sqlMapper.queryForObject("selectOne", getNo());

		//���� ���� ����
		File deleteFile = new File(fileUploadPath + resultClass.getFile_savname());
		deleteFile.delete();

		// ������ �׸� ����.
		paramClass.setNo(getNo());
				
		// ���� ���� ����.
		sqlMapper.update("deleteBoard", paramClass);

		return SUCCESS;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

