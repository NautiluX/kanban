package com.ntlx.boardtest;

public class BoardTestUtils {
	static TestBoard createBoardWith3Lanes()
	{
		TestBoard boardWithLanes = new TestBoard();
		boardWithLanes.addLane(new TestLane());
		boardWithLanes.addLane(new TestLane());
		boardWithLanes.addLane(new TestLane());
		return boardWithLanes;
	}
}
