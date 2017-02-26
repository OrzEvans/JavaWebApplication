package com.web.application.project.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 频率实体类
 * @author Evans
 * @date 2017年1月11日
 */
public class FrequencyStruct implements Serializable {
	
	private static final long serialVersionUID = -2766143184907005317L;


	private String uniqueKey;
	private long start;  //ip开始时间
	private long end;  //ip结束时间
	private long sessionStart;  //session开始时间
	private long sessionEnd;	//session结束时间
	private int time;  	//限制时间
	private int limit;	//限制次数
	private List<Long> accessPoints = new ArrayList<Long>();  //ip时间集合
	private List<String> sessionCounts = new ArrayList<String>();  //ip，session集合
	private Long forbidTime;
	private int forbidCount;

	private int forbidVisitCount;
	private long forbidVisitStart;
	private long forbidVisitEnd;
	private String message;

	private long IPforbidTime;
	private int IPforbidCount;

	public int getIPforbidCount() {
		return IPforbidCount;
	}

	public void setIPforbidCount() {
		this.IPforbidCount ++;
	}

	public long getIPforbidTime() {
		return IPforbidTime;
	}

	public void setIPforbidTime(long IPforbidTime) {
		this.IPforbidTime = IPforbidTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getForbidVisitCount() {
		return forbidVisitCount;
	}

	public void setForbidVisitCount() {
		this.forbidVisitCount ++;
	}

	public long getForbidVisitStart() {
		return forbidVisitStart;
	}

	public void setForbidVisitStart(long forbidVisitStart) {
		this.forbidVisitStart = forbidVisitStart;
	}

	public long getForbidVisitEnd() {
		return forbidVisitEnd;
	}

	public void setForbidVisitEnd(long forbidVisitEnd) {
		this.forbidVisitEnd = forbidVisitEnd;
	}

	public void reset(long timeMillis) {
		start = end = timeMillis;
		accessPoints.clear();
		accessPoints.add(timeMillis);
	}

	public void resetSession(long timeMillis,String session){
		sessionStart=sessionEnd=timeMillis;
		sessionCounts.clear();
		sessionCounts.add(session);
	}

	public void resetVisit(long currentTimeMilles) {
		forbidVisitCount=0;
		forbidVisitStart=forbidVisitEnd=currentTimeMilles;
	}

	public void resetForbid(){
		this.forbidCount=0;
		this.forbidTime=0L;
	}
	public void resetIpForbid(){
		this.IPforbidCount=0;
		this.IPforbidTime=0L;
	}
	public Long getForbidTime() {
		return forbidTime;
	}

	public void setForbidTime(Long forbidTime) {
		this.forbidTime = forbidTime;
	}

	public int getForbidCount() {
		return forbidCount;
	}

	public void setForbidCount(int forbidCount) {
		this.forbidCount = forbidCount;
	}

	public void addForbidCount(){
		this.forbidCount++;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(long sessionStart) {
		this.sessionStart = sessionStart;
	}

	public long getSessionEnd() {
		return sessionEnd;
	}

	public void setSessionEnd(long sessionEnd) {
		this.sessionEnd = sessionEnd;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Long> getAccessPoints() {
		return accessPoints;
	}

	public void setAccessPoints(List<Long> accessPoints) {
		this.accessPoints = accessPoints;
	}

	public List<String> getSessionCounts() {
		return sessionCounts;
	}

	public void setSessionCounts(List<String> sessionCounts) {
		this.sessionCounts = sessionCounts;
	}


}
