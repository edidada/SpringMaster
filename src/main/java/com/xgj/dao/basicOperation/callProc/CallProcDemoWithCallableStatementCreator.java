package com.xgj.dao.basicOperation.callProc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.CallableStatementCreatorFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import com.xgj.dao.demo.BaseDao;

@Repository
public class CallProcDemoWithCallableStatementCreator extends BaseDao {

	private static final String PROCSQL_STRING = "call PROC_artisan_selectInfo_withId(?,?,?)";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void printUserInfo(int userId) {

		// ʹ��CallableStatementCreatorFactory ���� CallableStatementCreator
		CallableStatementCreatorFactory factory = new CallableStatementCreatorFactory(
				PROCSQL_STRING);
		// �������
		factory.addParameter(new SqlParameter("p_user_id", Types.INTEGER));
		// ���ó���
		factory.addParameter(new SqlOutParameter("o_username", Types.VARCHAR));
		factory.addParameter(new SqlOutParameter("o_password", Types.VARCHAR));

		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("p_user_id", userId);

		CallableStatementCreator csc = factory
				.newCallableStatementCreator(paramMap);

		String userInfo = jdbcTemplate.execute(csc,
				new CallableStatementCallback<String>() {

					@Override
					public String doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {
						// ִ��
						cs.execute();
						// ��ȡ���ؽ��
						String userName = cs.getString(2);
						String password = cs.getString(3);
						String returnInfo = userName + "|" + password;
						return returnInfo;
					}
				});

		System.out.println("UserInfo:" + userInfo);
	}
}
