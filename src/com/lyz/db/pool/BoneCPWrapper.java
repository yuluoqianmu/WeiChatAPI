package com.lyz.db.pool;

import java.lang.ref.Reference;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jsr166y.LinkedTransferQueue;

import com.google.common.base.FinalizableReferenceQueue;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.StatementHandle;
import com.jolbox.bonecp.Statistics;
/**
 * 
 * @author luyongzhao
 *
 */
public class BoneCPWrapper extends BoneCP{
	
	private final static Log LOG = LogFactory.getLog(BoneCPWrapper.class);

	public BoneCPWrapper(BoneCPConfig arg0) throws SQLException {
		super(arg0);
	}

	@Override
	public String captureStackTrace(String arg0) {
		// TODO Auto-generated method stub
		return super.captureStackTrace(arg0);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public Future<Connection> getAsyncConnection() {
		// TODO Auto-generated method stub
		return super.getAsyncConnection();
	}

	@Override
	public BoneCPConfig getConfig() {
		// TODO Auto-generated method stub
		return super.getConfig();
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return super.getConnection();
	}

	@Override
	public FinalizableReferenceQueue getFinalizableRefQueue() {
		// TODO Auto-generated method stub
		return super.getFinalizableRefQueue();
	}

	@Override
	public Map<Connection, Reference<ConnectionHandle>> getFinalizableRefs() {
		// TODO Auto-generated method stub
		return super.getFinalizableRefs();
	}

	@Override
	public ExecutorService getReleaseHelper() {
		// TODO Auto-generated method stub
		return super.getReleaseHelper();
	}

	@Override
	public ExecutorService getStatementCloseHelperExecutor() {
		// TODO Auto-generated method stub
		return super.getStatementCloseHelperExecutor();
	}

	@Override
	public LinkedTransferQueue<StatementHandle> getStatementsPendingRelease() {
		// TODO Auto-generated method stub
		return super.getStatementsPendingRelease();
	}

	@Override
	public Statistics getStatistics() {
		// TODO Auto-generated method stub
		return super.getStatistics();
	}

	@Override
	public int getTotalCreatedConnections() {
		// TODO Auto-generated method stub
		return super.getTotalCreatedConnections();
	}

	@Override
	public int getTotalFree() {
		// TODO Auto-generated method stub
		return super.getTotalFree();
	}

	@Override
	public int getTotalLeased() {
		// TODO Auto-generated method stub
		return super.getTotalLeased();
	}

	@Override
	public void initJMX() {
		// TODO Auto-generated method stub
		super.initJMX();
	}

	@Override
	public void initStmtReleaseHelper(String arg0) {
		// TODO Auto-generated method stub
		super.initStmtReleaseHelper(arg0);
	}

	@Override
	public void internalReleaseConnection(ConnectionHandle arg0)
			throws SQLException {
		// TODO Auto-generated method stub
		super.internalReleaseConnection(arg0);
	}

	@Override
	public boolean isConnectionHandleAlive(ConnectionHandle arg0) {
		// TODO Auto-generated method stub
		return super.isConnectionHandleAlive(arg0);
	}

	@Override
	public boolean isReleaseHelperThreadsConfigured() {
		// TODO Auto-generated method stub
		return super.isReleaseHelperThreadsConfigured();
	}

	@Override
	public boolean isStatementReleaseHelperThreadsConfigured() {
		// TODO Auto-generated method stub
		return super.isStatementReleaseHelperThreadsConfigured();
	}

	@Override
	public Connection obtainRawInternalConnection() throws SQLException {
		// TODO Auto-generated method stub
		return super.obtainRawInternalConnection();
	}

	@Override
	public void postDestroyConnection(ConnectionHandle handle) {
		// TODO Auto-generated method stub
		super.postDestroyConnection(handle);
	}

	@Override
	public void putConnectionBackInPartition(
			ConnectionHandle connectionHandle) throws SQLException {
		// TODO Auto-generated method stub
		super.putConnectionBackInPartition(connectionHandle);
	}

	@Override
	public void releaseConnection(Connection connection){
		
		try {
			super.releaseConnection(connection);
		} catch (SQLException e) {
			LOG.error("fail to release connection", e);
		}
	}

	@Override
	public void setReleaseHelper(ExecutorService releaseHelper) {
		// TODO Auto-generated method stub
		super.setReleaseHelper(releaseHelper);
	}

	@Override
	public void setStatementCloseHelperExecutor(
			ExecutorService statementCloseHelper) {
		// TODO Auto-generated method stub
		super.setStatementCloseHelperExecutor(statementCloseHelper);
	}

	@Override
	public synchronized void shutdown() {
		// TODO Auto-generated method stub
		super.shutdown();
	}

	@Override
	public void terminateAllConnections() {
		// TODO Auto-generated method stub
		super.terminateAllConnections();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	

}
