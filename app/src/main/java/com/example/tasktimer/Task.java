package com.example.tasktimer;

import java.io.Serializable;

public class Task implements Serializable {
	public static final Long serialVersionUID = 15042020L;

	private long m_Id;
	public final String mName;
	public final String mDescription;
	public final int mSortOrder;

	public Task(long m_Id, String mName, String mDescription, int mSortOrder) {
		this.m_Id = m_Id;
		this.mName = mName;
		this.mDescription = mDescription;
		this.mSortOrder = mSortOrder;
	}

	public long getId() {
		return m_Id;
	}

	public String getName() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getSortOrder() {
		return mSortOrder;
	}

	public void setId(long m_Id) {
		this.m_Id = m_Id;
	}

	@Override
	public String toString() {
		return "Task{" +
				"m_Id=" + m_Id +
				", mName='" + mName + '\'' +
				", mDescription='" + mDescription + '\'' +
				", mSortOrder=" + mSortOrder +
				'}';
	}
}
