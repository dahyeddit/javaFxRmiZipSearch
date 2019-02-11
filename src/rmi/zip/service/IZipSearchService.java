package rmi.zip.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rmi.zip.vo.ZipVO;

// RMI용으로 만든다.
public interface IZipSearchService extends Remote{
	/**
	 * 동이름을 문자열로 받아서 해당 데이터를 검색하는 메서드
	 * @param dong 검색할 동이름
	 * @return 검색된 우편번호정보를 갖는 List
	 * 리모트사용하려면 throws해주기
	 */
	public List<ZipVO> getZipSearchDong(String dong) throws RemoteException;

	/**
	 * 우편번호의 일부분을 매개변수로 받아서 해당 데이터를 검색하는 메서드
	 * @param code 검색할 우편번호 일부분
	 * @return 검색된 우편번호정보 갖는 List
	 */
	public List<ZipVO> getZipSearchCode(String code) throws RemoteException;
}
