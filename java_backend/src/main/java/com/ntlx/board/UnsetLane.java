package com.ntlx.board;

public class UnsetLane extends Lane {
	public static final Lane Instance = new UnsetLane();
	public UnsetLane() {
		super(-1, "Lane Not Set");
	}

}