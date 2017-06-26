package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardSelectionSqlStatement {
	String baseSql = "SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID";
	String laneCardsSql = " WHERE LANE_ID = ?";
	String archivedOnlySql = " AND IS_ARCHIVED IS ?";
	String laneCardsSqlTag = " AND CONTENT LIKE ?";
	
	String tag;
	boolean isShowArchived;
	int laneId;
	
	int idxTag = 2;
	
	public CardSelectionSqlStatement(boolean isShowArchived, String tag, int laneId) {
		this.tag = tag;
		this.isShowArchived = isShowArchived;
		this.laneId = laneId;
	}
	
	public PreparedStatement prepareStatement(Database database) throws SQLException {
		String sql = getSql();
		PreparedStatement statement = database.prepareStatement(sql);
		statement.setInt(1, laneId);
		if (!isShowArchived) {
			statement.setNull(2, java.sql.Types.INTEGER);
		}
		if (tag != null) {
			statement.setString(idxTag, tag);
		}
		return statement;
	}
	
	public String getSql() {
		String sql = baseSql + laneCardsSql;
		if (!isShowArchived) {
			sql += archivedOnlySql;
			idxTag++;
		}
		if (tag != null) {
			sql += laneCardsSqlTag;
		}
		return sql;
	}
}
